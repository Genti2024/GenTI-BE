package com.gt.genti.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.dto.CreatorFindResponseDto;
import com.gt.genti.dto.AccountUpdateRequestDto;
import com.gt.genti.dto.CreatorStatusUpdateRequestDto;
import com.gt.genti.dto.CreatorStatusUpdateResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatorService {
	private final CreatorRepository creatorRepository;

	public CreatorFindResponseDto getCreatorInfo(Long userId) {
		Creator foundCreator = findCreatorByUserId(userId);
		return CreatorFindResponseDto.builder()
			.bankType(foundCreator.getBankType())
			.accountNumber(foundCreator.getAccountNumber())
			.accountHolder(foundCreator.getAccountHolder())
			.workable(foundCreator.getWorkable())
			.build();
	}

	@Transactional
	public Boolean updateAccountInfo(Long userId, AccountUpdateRequestDto dto) {
		Creator foundCreator = findCreatorByUserId(userId);
		foundCreator.updateAccountInfo(dto.getBankType(),
			dto.getAccountNumber(), dto.getAccountHolder());
		return true;
	}

	@Transactional
	public CreatorStatusUpdateResponseDto updateCreatorStatus(Long userId,
		CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		Creator foundCreator = findCreatorByUserId(userId);
		foundCreator.setWorkable(creatorStatusUpdateRequestDto.getWorkable());
		return CreatorStatusUpdateResponseDto.builder().workable(foundCreator.getWorkable()).build();
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.CreatorNotFound));
	}
}
