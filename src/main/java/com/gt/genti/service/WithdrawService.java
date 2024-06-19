package com.gt.genti.service;

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
import com.gt.genti.dto.admin.response.WithdrawFindResponseDto;
import com.gt.genti.dto.creator.response.WithdrawCreateResponseDto;
import com.gt.genti.error.DomainErrorCode;
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
	public WithdrawCreateResponseDto create(User user) {
		Creator foundCreator = findCreatorByUser(user);
		List<Settlement> foundSettlementList = settlementRepository.findAllWithdrawableByCreatorOrderByCreatedAtDesc(
			foundCreator);

		if (foundSettlementList.isEmpty()) {
			throw ExpectedException.withLogging(DomainErrorCode.NoSettlementForWithdrawalException);
		}

		WithdrawRequest withdrawRequest = new WithdrawRequest(foundCreator);
		foundSettlementList.forEach(settlement -> {
			settlement.requestWithdraw(withdrawRequest);
			withdrawRequest.update(settlement.getReward());
		});

		WithdrawRequest savedWithdrawRequest = withdrawRequestRepository.save(withdrawRequest);
		return new WithdrawCreateResponseDto(savedWithdrawRequest);
	}

	private Creator findCreatorByUser(User user) {
		return creatorRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.CreatorNotFound));
	}

	public List<WithdrawCreateResponseDto> findWithdrawList(User user) {
		Creator foundCreator = findCreatorByUser(user);
		return withdrawRequestRepository.findAllByCreatorOrderByCreatedAtDesc(foundCreator)
			.stream()
			.map(WithdrawCreateResponseDto::new)
			.toList();
	}

	public Page<WithdrawFindResponseDto> getAllWithdrawRequests(Pageable pageable, String statusString) {
		if (statusString.equals("ALL")) {
			return withdrawRequestRepository.findAll(pageable).map(WithdrawFindResponseDto::of);
		} else {
			return withdrawRequestRepository.findAllByStatus(pageable, WithdrawRequestStatus.valueOf(statusString))
				.map(WithdrawFindResponseDto::of);
		}
	}

	@Transactional
	public WithdrawCompletionResponseDto complete(Long withdrawRequestId, User user) {
		WithdrawRequest foundWR = findWithdrawRequest(withdrawRequestId);
		User foundAdminUser = userRepository.findById(user.getId())
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.UserNotFound));
		foundWR.complete(foundAdminUser);
		Deposit foundDeposit = depositRepository.findByCreator(foundWR.getCreator())
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.DepositNotFound));

		foundDeposit.completeWithdraw(foundWR.getAmount());
		return WithdrawCompletionResponseDto.builder()
			.id(foundWR.getId())
			.modifiedByUsername(foundWR.getModifiedBy().getUsername())
			.status(foundWR.getStatus())
			.build();
	}

	private WithdrawRequest findWithdrawRequest(Long withdrawRequestId) {
		return withdrawRequestRepository.findById(withdrawRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.WithdrawRequestNotFound));
	}
}