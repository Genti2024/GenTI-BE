package com.gt.genti.cashout.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.cashout.dto.response.CashoutCompletionResponseDto;
import com.gt.genti.cashout.dto.response.CashoutFindByAdminResponseDto;
import com.gt.genti.cashout.dto.response.CashoutFindByCreatorResponseDto;
import com.gt.genti.cashout.model.Cashout;
import com.gt.genti.cashout.model.CashoutStatus;
import com.gt.genti.cashout.repository.CashoutRepository;
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

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CashoutService {
	private final SettlementRepository settlementRepository;
	private final CashoutRepository cashoutRepository;
	private final CreatorRepository creatorRepository;
	private final UserRepository userRepository;
	private final DepositRepository depositRepository;

	public CashoutFindByCreatorResponseDto create(Long user) {
		Creator foundCreator = findCreatorByUserId(user);
		List<Settlement> foundSettlementList = settlementRepository.findAllValidSettlementByCreatorOrderByCreatedAtDesc(
			foundCreator);

		if (foundSettlementList.isEmpty()) {
			throw ExpectedException.withoutLogging(ResponseCode.CannotRequestCashoutDueToSettlementsNotAvailable);
		}

		Cashout cashout = new Cashout(foundCreator);
		foundSettlementList.forEach(settlement -> {
			settlement.setCashout(cashout);
			cashout.addSettlement(settlement.getReward());
		});

		Cashout savedCashout = cashoutRepository.save(cashout);
		return mapToFindByCreatorResponseDto(savedCashout);
	}

	private static CashoutFindByCreatorResponseDto mapToFindByCreatorResponseDto(Cashout savedCashout) {
		return CashoutFindByCreatorResponseDto.builder()
			.cashoutId(savedCashout.getId())
			.amount(savedCashout.getAmount())
			.taskCount(savedCashout.getTaskCount())
			.cashoutStatus(savedCashout.getStatus())
			.build();
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CreatorNotFound, userId));
	}

	public Page<CashoutFindByCreatorResponseDto> findCashoutList(Long userId, Pageable pageable) {
		Creator foundCreator = findCreatorByUserId(userId);
		return cashoutRepository.findAllByCreator(foundCreator, pageable)
			.map(CashoutService::mapToFindByCreatorResponseDto);
	}

	public Page<CashoutFindByAdminResponseDto> getAllCashout(Pageable pageable, String statusString) {
		if (statusString.equals("ALL")) {
			return cashoutRepository.findAll(pageable).map(CashoutFindByAdminResponseDto::of);
		} else {
			return cashoutRepository.findAllByStatus(pageable, CashoutStatus.valueOf(statusString))
				.map(CashoutFindByAdminResponseDto::of);
		}
	}

	public Page<CashoutFindByAdminResponseDto> getCashoutByCreatorEmail(String email, Pageable pageable,
		String statusString) {
		User foundUser = userRepository.findByEmail(email)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFoundByEmail, email));
		if (statusString.equals("ALL")) {
			return cashoutRepository.findAllByCreatedBy(foundUser, pageable).map(CashoutFindByAdminResponseDto::of);
		} else {
			return cashoutRepository.findAllByCreatedByAndStatus(foundUser, pageable,
				CashoutStatus.valueOf(statusString)).map(CashoutFindByAdminResponseDto::of);
		}
	}

	public CashoutCompletionResponseDto complete(Long cashoutId, Long userId) {
		Cashout foundCashout = getCashout(cashoutId);
		User foundAdminUser = userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
		foundCashout.complete(foundAdminUser);
		Deposit foundDeposit = depositRepository.findByCreator(foundCashout.getCreator())
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.DepositNotFound));

		foundDeposit.completeCashout(foundCashout.getAmount());
		return CashoutCompletionResponseDto.builder()
			.cashoutId(foundCashout.getId())
			.modifiedBy(foundCashout.getModifiedBy().getUsername())
			.status(foundCashout.getStatus())
			.build();
	}

	private Cashout getCashout(Long cashoutId) {
		return cashoutRepository.findById(cashoutId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CashoutNotFound));
	}
}
