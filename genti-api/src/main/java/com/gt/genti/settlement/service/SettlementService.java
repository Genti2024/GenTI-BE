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
import com.gt.genti.settlementandwithdraw.dto.response.SettlementAndWithdrawPageResponseDto;
import com.gt.genti.settlementanwithdraw.model.SettlementAndWithdraw;
import com.gt.genti.settlementanwithdraw.repository.SettlementAndWithdrawalRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementService {
	private final CreatorRepository creatorRepository;
	private final DepositRepository depositRepository;
	private final SettlementAndWithdrawalRepositoryCustom settlementAndWithdrawalRepositoryCustom;

	public SettlementAndWithdrawPageResponseDto getAllSettlements(Long userId, Pageable pageable) {
		Creator foundCreator = findCreatorByUserId(userId);
		Deposit foundDeposit = findDepositByCreator(foundCreator);

		return new SettlementAndWithdrawPageResponseDto(foundDeposit,
			settlementAndWithdrawalRepositoryCustom.findSettlementAndWithdrawByCreatorPagination(foundCreator.getId(),
					pageable)
				.map(SettlementAndWithdraw::new));
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
