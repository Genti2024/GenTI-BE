package com.gt.genti.withdraw.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.deposit.repository.DepositRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.settlement.model.Settlement;
import com.gt.genti.settlement.repository.SettlementRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.withdraw.dto.response.WithdrawCompletionResponseDto;
import com.gt.genti.withdraw.dto.response.WithdrawFindByAdminResponseDto;
import com.gt.genti.withdraw.dto.response.WithdrawFindByCreatorResponseDto;
import com.gt.genti.withdrawrequest.model.WithdrawRequest;
import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;
import com.gt.genti.withdrawrequest.repository.WithdrawRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WithdrawService {
	private final SettlementRepository settlementRepository;
	private final WithdrawRequestRepository withdrawRequestRepository;
	private final CreatorRepository creatorRepository;
	private final UserRepository userRepository;
	private final DepositRepository depositRepository;

	@Transactional
	public WithdrawFindByCreatorResponseDto create(Long user) {
		Creator foundCreator = findCreatorByUserId(user);
		List<Settlement> foundSettlementList = settlementRepository.findAllWithdrawableByCreatorOrderByCreatedAtDesc(
			foundCreator);

		if (foundSettlementList.isEmpty()) {
			throw ExpectedException.withoutLogging(ResponseCode.NoSettlementForWithdrawalException);
		}

		WithdrawRequest withdrawRequest = new WithdrawRequest(foundCreator);
		foundSettlementList.forEach(settlement -> {
			settlement.setWithdrawRequest(withdrawRequest);
			withdrawRequest.addSettlement(settlement.getReward());
		});

		WithdrawRequest savedWithdrawRequest = withdrawRequestRepository.save(withdrawRequest);
		return mapToFindByCreatorResponseDto(savedWithdrawRequest);
	}

	private static WithdrawFindByCreatorResponseDto mapToFindByCreatorResponseDto(
		WithdrawRequest savedWithdrawRequest) {
		return WithdrawFindByCreatorResponseDto.builder()
			.withdrawRequestId(savedWithdrawRequest.getId())
			.amount(savedWithdrawRequest.getAmount())
			.taskCount(savedWithdrawRequest.getTaskCount())
			.withdrawRequestStatus(savedWithdrawRequest.getStatus())
			.build();
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CreatorNotFound, userId));
	}

	public Page<WithdrawFindByCreatorResponseDto> findWithdrawList(Long userId, Pageable pageable) {
		Creator foundCreator = findCreatorByUserId(userId);
		return withdrawRequestRepository.findAllByCreator(foundCreator, pageable)
			.map(WithdrawService::mapToFindByCreatorResponseDto);
	}

	public Page<WithdrawFindByAdminResponseDto> getAllWithdrawRequests(Pageable pageable,
		String statusString) {
		if (statusString.equals("ALL")) {
			return withdrawRequestRepository.findAll(pageable).map(
				WithdrawFindByAdminResponseDto::of);
		} else {
			return withdrawRequestRepository.findAllByStatus(pageable, WithdrawRequestStatus.valueOf(statusString))
				.map(WithdrawFindByAdminResponseDto::of);
		}
	}

	@Transactional
	public WithdrawCompletionResponseDto complete(Long withdrawRequestId, Long userId) {
		WithdrawRequest foundWR = findWithdrawRequest(withdrawRequestId);
		User foundAdminUser = userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
		foundWR.complete(foundAdminUser);
		Deposit foundDeposit = depositRepository.findByCreator(foundWR.getCreator())
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.DepositNotFound));

		foundDeposit.completeWithdraw(foundWR.getAmount());
		return WithdrawCompletionResponseDto.builder()
			.withdrawRequestId(foundWR.getId())
			.modifiedBy(foundWR.getModifiedBy().getUsername())
			.status(foundWR.getStatus())
			.build();
	}

	private WithdrawRequest findWithdrawRequest(Long withdrawRequestId) {
		return withdrawRequestRepository.findById(withdrawRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.WithdrawRequestNotFound));
	}
}
