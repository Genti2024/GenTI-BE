package com.gt.genti.application.service;

import static com.gt.genti.other.util.TimeUtils.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateResponseSubmitDto;
import com.gt.genti.dto.UpdateMemoRequestDto;
import com.gt.genti.dto.UpdatePictureUrlRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.util.TimeUtils;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.DepositRepository;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.SettlementRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateWorkService {

	private final PictureService pictureService;
	private final CreatorRepository creatorRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final SettlementRepository settlementRepository;
	private final DepositRepository depositRepository;
	private final RequestMatchService requestMatchService;

	public PictureGenerateRequestBriefResponseDto getPictureGenerateRequestBrief(Long userId,
		PictureGenerateRequestStatus status) {
		Creator foundCreator = findCreatorByUserId(userId);
		Optional<PictureGenerateRequest> foundPGR;
		switch (status) {
			case IN_PROGRESS ->
				foundPGR = pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
					foundCreator);
			case ASSIGNING ->
				foundPGR = pictureGenerateRequestRepository.findByStatusOrderByCreatedAtDesc(foundCreator, status);
			default -> throw new ExpectedException(ErrorCode.NotSupportedTemp);
		}

		return foundPGR.map(PictureGenerateRequestBriefResponseDto::new)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));

	}

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestDetail(Long userId,
		Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUserId(userId);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (foundPictureGenerateRequest.getCreator() == null) {
			throw new ExpectedException(ErrorCode.NotMatchedYet);
		} else if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw new ExpectedException(ErrorCode.NotAssignedToMe);
		}

		return new PictureGenerateRequestDetailResponseDto(foundPictureGenerateRequest);

	}

	public List<PictureGenerateRequestDetailResponseDto> getPictureGenerateRequestDetailAll(Long creatorId) {
		Creator foundCreator = findCreatorByUserId(creatorId);
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PictureGenerateRequestDetailResponseDto::new).toList();

	}

	@Transactional
	public PictureGenerateResponseSubmitDto submit(Long userId, Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));
		Creator foundCreator = creatorRepository.findByUserId(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.CreatorNotFound));

		if (!Objects.equals(foundPGRES.getCreator().getId(), foundCreator.getId())) {
			throw new ExpectedException(ErrorCode.NotAssignedToMe);
		}

		foundPGRES.updateStatus(PictureGenerateResponseStatus.SUBMITTED_FINAL);

		LocalDateTime createdAt = foundPGRES.getCreatedAt();
		Duration elapsedDuration = Duration.between(createdAt, LocalDateTime.now());

		if (elapsedDuration.compareTo(Duration.ofHours(TimeUtils.PGRES_LIMIT_HOUR)) > 0) {
			throw new ExpectedException(ErrorCode.ExpiredPictureGenerateRequest);
		}
		Long reword = calculateReward(elapsedDuration.toMinutes());
		Settlement settlement = Settlement.builder()
			.pictureGenerateResponse(foundPGRES)
			.elapsed(elapsedDuration)
			.reward(reword).build();

		settlementRepository.save(settlement);
		Deposit foundDeposit = depositRepository.findByUserId(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.DepositNotFound));
		foundDeposit.add(reword);
		return PictureGenerateResponseSubmitDto.builder()
			.elapsedTime(getTimeString(elapsedDuration))
			.build();
	}

	@Transactional
	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<UpdatePictureUrlRequestDto> updatePictureUrlRequestDtoList, Long uploaderId) {
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));

		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = updatePictureUrlRequestDtoList.stream()
			.map(command -> CreatePictureCreatedByCreatorCommand.builder()
				.url(command.getUrl())
				.userId(uploaderId)
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

	public Boolean acceptPictureGenerateRequest(Long userId, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser().getId(), userId)) {
			throw new ExpectedException(ErrorCode.NotAssignedToMe);
		}
		foundPictureGenerateRequest.accept();
		return true;
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.CreatorNotFound));
	}

	private PictureGenerateRequest findPGREQ(Long pictureGenerateRequestId) {
		return pictureGenerateRequestRepository.findById(
				pictureGenerateRequestId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));
	}

	@Transactional
	public Boolean rejectPictureGenerateRequest(Long userId, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser().getId(), userId)) {
			throw new ExpectedException(ErrorCode.NotAssignedToMe);
		}

		foundPictureGenerateRequest.reject();
		requestMatchService.matchPictureGenerateRequest(foundPictureGenerateRequest);
		return true;
	}
}

