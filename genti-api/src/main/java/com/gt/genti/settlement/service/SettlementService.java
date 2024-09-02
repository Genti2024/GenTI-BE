package com.gt.genti.settlement.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.deposit.repository.DepositRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.settlementandcashout.dto.response.SettlementAndCashoutPageResponseDto;
import com.gt.genti.settlementandcashout.model.SettlementAndCashout;
import com.gt.genti.settlementandcashout.repository.SettlementAndCashoutRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementService {
	private final CreatorRepository creatorRepository;
	private final DepositRepository depositRepository;
	private final SettlementAndCashoutRepositoryCustom settlementAndCashoutRepositoryCustom;

	public SettlementAndCashoutPageResponseDto getAllSettlements(Long userId, Pageable pageable) {
		Creator foundCreator = findCreatorByUserId(userId);
		Deposit foundDeposit = findDepositByCreator(foundCreator);

		return new SettlementAndCashoutPageResponseDto(foundDeposit,
			settlementAndCashoutRepositoryCustom.findSettlementAndCashoutByCreatorPagination(foundCreator.getId(),
					pageable)
				.map(SettlementAndCashout::new));
	}

	private Deposit findDepositByCreator(Creator foundCreator) {
		return depositRepository.findByCreator(foundCreator)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.DepositNotFound));
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CreatorNotFound, userId));
	}
}
