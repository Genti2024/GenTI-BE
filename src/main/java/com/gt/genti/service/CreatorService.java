package com.gt.genti.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.dto.CreatorInfoResponseDto;
import com.gt.genti.dto.UpdateAccountInfoRequestDto;
import com.gt.genti.dto.UpdateCreatorStatusRequestDto;
import com.gt.genti.dto.UpdateCreatorStatusResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatorService {
	private final CreatorRepository creatorRepository;

	public CreatorInfoResponseDto getCreatorInfo(Long userId) {
		Creator foundCreator = findCreatorByUserId(userId);
		return CreatorInfoResponseDto.builder()
			.bankType(foundCreator.getBankType())
			.accountNumber(foundCreator.getAccountNumber())
			.accountHolder(foundCreator.getAccountHolder())
			.workable(foundCreator.getWorkable())
			.build();
	}

	@Transactional
	public Boolean updateAccountInfo(Long userId, UpdateAccountInfoRequestDto dto) {
		Creator foundCreator = findCreatorByUserId(userId);
		foundCreator.updateAccountInfo(dto.getBankType(),
			dto.getAccountNumber(), dto.getAccountHolder());
		return true;
	}

	@Transactional
	public UpdateCreatorStatusResponseDto updateCreatorStatus(Long userId,
		UpdateCreatorStatusRequestDto updateCreatorStatusRequestDto) {
		Creator foundCreator = findCreatorByUserId(userId);
		foundCreator.setWorkable(updateCreatorStatusRequestDto.getWorkable());
		return UpdateCreatorStatusResponseDto.builder().workable(foundCreator.getWorkable()).build();
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.CreatorNotFound));
	}
}
