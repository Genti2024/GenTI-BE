package com.gt.genti.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.external.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.external.aws.service.S3Service;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.PictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateWorkService {
	private final S3Service s3Service;

	private final PictureRepository pictureRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	public PictureGenerateRequestBriefResponseDto getPictureGenerateRequestBrief(Long creatorId,
		PictureGenerateRequestStatus pictureGenerateRequestStatus) {
		if (pictureGenerateRequestStatus.equals(PictureGenerateRequestStatus.BEFORE_WORK)) {
			return pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
				creatorId).map(
				(pictureGenerateRequest) ->
					PictureGenerateRequestBriefResponseDto.builder()
						.requestId(pictureGenerateRequest.getId())
						.cameraAngle(pictureGenerateRequest.getCameraAngle().getStringValue())
						.shotCoverage(pictureGenerateRequest.getShotCoverage().getStringValue())
						.prompt(pictureGenerateRequest.getPrompt())
						.build()
			).orElseThrow(() -> new ExpectedException(ErrorCode.ActivePictureGenerateRequestNotExists));
		}
		throw new ExpectedException(ErrorCode.NotSupportedTemp);
	}

	public List<PictureGenerateRequestDetailResponseDto> getPictureGenerateRequestDetailAll(Long creatorId) {
		pictureGenerateResponseRepository.findByCreator
	}

	@Transactional
	public List<PreSignedUrlResponseDto> getUploadUrl(Long pictureGenerateResponseId,
		List<PreSignedUrlRequestDto> preSignedUrlRequestDto) {

		List<PreSignedUrlResponseDto> results = s3Service.getPreSignedUrlMany(preSignedUrlRequestDto);

		PictureGenerateResponse findPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));

		List<PictureCompleted> uploadPictureListCreated = new ArrayList<>();
		results.forEach(
			dto -> uploadPictureListCreated.add(new PictureCompleted(dto.getS3Key(), findPictureGenerateResponse)));

		pictureRepository.saveAll(uploadPictureListCreated);
		return results;

	}

	@Transactional
	public Boolean submit(Long pictureGenerateResponseId) {
		PictureGenerateResponse pictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));
		pictureGenerateResponse.updateStatus(PictureGenerateResponseStatus.SUBMITTED_FINAL);
		return true;
	}
}

