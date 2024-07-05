package com.gt.genti.service;

import static com.gt.genti.error.ResponseCode.*;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.User;
import com.gt.genti.dto.creator.response.SettlementAndWithdraw;
import com.gt.genti.dto.creator.response.SettlementAndWithdrawPageResponseDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.DepositRepository;
import com.gt.genti.repository.SettlementAndWithdrawalRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {
	private final CreatorRepository creatorRepository;
	private final DepositRepository depositRepository;
	private final SettlementAndWithdrawalRepositoryCustom settlementAndWithdrawalRepositoryCustom;

	public SettlementAndWithdrawPageResponseDto getAllSettlements(User user, Pageable pageable) {
		Creator foundCreator = findCreatorByUser(user);
		Deposit foundDeposit = findDepositByCreator(foundCreator);

		return new SettlementAndWithdrawPageResponseDto(foundDeposit,
			settlementAndWithdrawalRepositoryCustom.findSettlementAndWithdrawByCreatorPagination(foundCreator.getId(),
					pageable)
				.map(SettlementAndWithdraw::new));
	}

	private Deposit findDepositByCreator(Creator foundCreator) {
		return depositRepository.findByCreator(foundCreator)
			.orElseThrow(() -> ExpectedException.withLogging(DepositNotFound));
	}

	private Creator findCreatorByUser(User user) {
		return creatorRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(CreatorNotFound, user.getId().toString()));
	}
}
