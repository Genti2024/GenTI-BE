package com.gt.genti.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.UpdateMemoRequestDto;
import com.gt.genti.dto.UpdatePictureUrlRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateWorkService {

	private final PictureService pictureService;
	private final CreatorRepository creatorRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	public PictureGenerateRequestBriefResponseDto getPictureGenerateRequestBrief(Long creatorId,
		PictureGenerateRequestStatus status) {
		Creator foundCreator = creatorRepository.findByUserId(creatorId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.CreatorNotFound));
		Optional<PictureGenerateRequest> foundPGR;
		switch (status) {
			case IN_PROGRESS ->
				foundPGR = pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
					foundCreator);
			case ASSIGNING ->
				foundPGR = pictureGenerateRequestRepository.findByStatusOrderByCreatedAtDesc(foundCreator, status);
			default -> throw new ExpectedException(ErrorCode.NotSupportedTemp);
		}

		return foundPGR.map(
			(pictureGenerateRequest) ->
				PictureGenerateRequestBriefResponseDto.builder()
					.requestId(pictureGenerateRequest.getId())
					.cameraAngle(pictureGenerateRequest.getCameraAngle())
					.shotCoverage(pictureGenerateRequest.getShotCoverage())
					.prompt(pictureGenerateRequest.getPrompt())
					.build()
		).orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));

	}

	public List<PictureGenerateRequestDetailResponseDto> getPictureGenerateRequestDetailAll(Long creatorId) {
		Creator foundCreator = creatorRepository.findByUserId(creatorId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.CreatorNotFound));
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PictureGenerateRequestDetailResponseDto::new).toList();

	}

	@Transactional
	public Boolean submit(Long pictureGenerateResponseId) {
		PictureGenerateResponse pictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));
		pictureGenerateResponse.updateStatus(PictureGenerateResponseStatus.SUBMITTED_FINAL);
		return true;
	}

	@Transactional
	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<UpdatePictureUrlRequestDto> updatePictureUrlRequestDtoList, Long uploaderId) {
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));

		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = updatePictureUrlRequestDtoList.stream()
			.map(d -> CreatePictureCreatedByCreatorCommand.builder()
				.url(d.getUrl())
				.uploadedBy(uploaderId)
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.build())
			.toList();

		pictureService.updateAll(newUploadPictures);
		return true;
	}

	@Transactional
	public Boolean updateMemo(Long pictureGenerateResponseId, UpdateMemoRequestDto updateMemoRequestDto) {
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));
		foundPictureGenerateResponse.updateMemo(updateMemoRequestDto.getMemo());
		return true;
	}
}

