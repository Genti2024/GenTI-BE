package com.gt.genti.creator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.dto.request.AccountUpdateRequestDto;
import com.gt.genti.creator.dto.request.CreatorStatusUpdateRequestDto;
import com.gt.genti.creator.dto.response.CreatorFindResponseDto;
import com.gt.genti.creator.dto.response.CreatorStatusUpdateResponseDto;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatorService {
	private final CreatorRepository creatorRepository;

	public CreatorFindResponseDto getCreatorInfo(Long userId) {
		Creator foundCreator = getCreatorByUserId(userId);
		return CreatorFindResponseDto.builder()
			.bankType(foundCreator.getBankType())
			.accountNumber(foundCreator.getAccountNumber())
			.accountHolder(foundCreator.getAccountHolder())
			.workable(foundCreator.getWorkable())
			.completedTaskCount(foundCreator.getCompletedTaskCount())
			.creatorId(foundCreator.getId())
			.build();
	}

	public Boolean updateAccountInfo(Long userId, AccountUpdateRequestDto dto) {
		Creator foundCreator = getCreatorByUserId(userId);
		foundCreator.updateAccountInfo(dto.getBankType(),
			dto.getAccountNumber(), dto.getAccountHolder());
		return true;
	}

	public CreatorStatusUpdateResponseDto updateCreatorStatus(Long userId,
		CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		Creator foundCreator = getCreatorByUserId(userId);
		foundCreator.setWorkable(creatorStatusUpdateRequestDto.getWorkable());
		return CreatorStatusUpdateResponseDto.builder().workable(foundCreator.getWorkable()).build();
	}

	private Creator getCreatorByUserId(Long userId) {
		return creatorRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CreatorNotFound, userId));
	}
}
