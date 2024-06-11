package com.gt.genti.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.User;
import com.gt.genti.dto.creator.request.AccountUpdateRequestDto;
import com.gt.genti.dto.creator.request.CreatorStatusUpdateRequestDto;
import com.gt.genti.dto.creator.response.CreatorFindResponseDto;
import com.gt.genti.dto.creator.response.CreatorStatusUpdateResponseDto;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatorService {
	private final CreatorRepository creatorRepository;

	public CreatorFindResponseDto getCreatorInfo(User user) {
		Creator foundCreator = findCreatorByUser(user);
		return CreatorFindResponseDto.builder()
			.bankType(foundCreator.getBankType())
			.accountNumber(foundCreator.getAccountNumber())
			.accountHolder(foundCreator.getAccountHolder())
			.workable(foundCreator.getWorkable())
			.build();
	}

	@Transactional
	public Boolean updateAccountInfo(User user, AccountUpdateRequestDto dto) {
		Creator foundCreator = findCreatorByUser(user);
		foundCreator.updateAccountInfo(dto.getBankType(),
			dto.getAccountNumber(), dto.getAccountHolder());
		return true;
	}

	@Transactional
	public CreatorStatusUpdateResponseDto updateCreatorStatus(User user,
		CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		Creator foundCreator = findCreatorByUser(user);
		foundCreator.setWorkable(creatorStatusUpdateRequestDto.getWorkable());
		return CreatorStatusUpdateResponseDto.builder().workable(foundCreator.getWorkable()).build();
	}

	private Creator findCreatorByUser(User user) {
		return creatorRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.CreatorNotFound));
	}
}
