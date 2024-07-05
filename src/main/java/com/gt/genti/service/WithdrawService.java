package com.gt.genti.service;

import static com.gt.genti.error.ResponseCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.User;
import com.gt.genti.domain.WithdrawRequest;
import com.gt.genti.domain.enums.WithdrawRequestStatus;
import com.gt.genti.dto.admin.response.WithdrawCompletionResponseDto;
import com.gt.genti.dto.admin.response.WithdrawFindByAdminResponseDto;
import com.gt.genti.dto.creator.response.WithdrawFindByCreatorResponseDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.DepositRepository;
import com.gt.genti.repository.SettlementRepository;
import com.gt.genti.repository.UserRepository;
import com.gt.genti.repository.WithdrawRequestRepository;

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
	public WithdrawFindByCreatorResponseDto create(User user) {
		Creator foundCreator = findCreatorByUser(user);
		List<Settlement> foundSettlementList = settlementRepository.findAllWithdrawableByCreatorOrderByCreatedAtDesc(
			foundCreator);

		if (foundSettlementList.isEmpty()) {
			throw ExpectedException.withoutLogging(NoSettlementForWithdrawalException);
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

	private Creator findCreatorByUser(User user) {
		return creatorRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(CreatorNotFound, user.getId().toString()));
	}

	public Page<WithdrawFindByCreatorResponseDto> findWithdrawList(User user, Pageable pageable) {
		Creator foundCreator = findCreatorByUser(user);
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
	public WithdrawCompletionResponseDto complete(Long withdrawRequestId, User user) {
		WithdrawRequest foundWR = findWithdrawRequest(withdrawRequestId);
		User foundAdminUser = userRepository.findById(user.getId())
			.orElseThrow(() -> ExpectedException.withLogging(UserNotFound, user.getId().toString()));
		foundWR.complete(foundAdminUser);
		Deposit foundDeposit = depositRepository.findByCreator(foundWR.getCreator())
			.orElseThrow(() -> ExpectedException.withLogging(DepositNotFound));

		foundDeposit.completeWithdraw(foundWR.getAmount());
		return WithdrawCompletionResponseDto.builder()
			.withdrawRequestId(foundWR.getId())
			.modifiedBy(foundWR.getModifiedBy().getUsername())
			.status(foundWR.getStatus())
			.build();
	}

	private WithdrawRequest findWithdrawRequest(Long withdrawRequestId) {
		return withdrawRequestRepository.findById(withdrawRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(WithdrawRequestNotFound));
	}
}
