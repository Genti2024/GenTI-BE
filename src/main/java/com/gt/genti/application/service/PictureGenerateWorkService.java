package com.gt.genti.application.service;

import static com.gt.genti.other.util.TimeUtils.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.CreatePictureCompletedCommand;
import com.gt.genti.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.PGRESUpdateByAdminResponseDto;
import com.gt.genti.dto.PGRESUpdateByCreatorResponseDto;
import com.gt.genti.dto.MemoUpdateRequestDto;
import com.gt.genti.dto.PictureUrlUpdateRequestDto;
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

	public PGREQBriefFindByCreatorResponseDto getPictureGenerateRequestBrief(Long userId,
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

		return foundPGR.map(PGREQBriefFindByCreatorResponseDto::new)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));

	}

	public PGREQDetailFindResponseDto getPictureGenerateRequestDetail(Long userId,
		Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUserId(userId);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (foundPictureGenerateRequest.getCreator() == null) {
			throw new ExpectedException(ErrorCode.NotMatchedYet);
		} else if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw new ExpectedException(ErrorCode.NotAssignedToMe);
		}

		return new PGREQDetailFindResponseDto(foundPictureGenerateRequest);

	}

	public List<PGREQDetailFindResponseDto> getPictureGenerateRequestDetailAll(Long userId) {
		Creator foundCreator = findCreatorByUserId(userId);
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PGREQDetailFindResponseDto::new).toList();

	}

	@Transactional
	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<PictureUrlUpdateRequestDto> pictureUrlUpdateRequestDtoList, Long uploaderId) {
		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);

		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = pictureUrlUpdateRequestDtoList.stream()
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
	public Boolean updateMemo(Long pictureGenerateResponseId, MemoUpdateRequestDto memoUpdateRequestDto) {
		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);
		foundPictureGenerateResponse.updateMemo(memoUpdateRequestDto.getMemo());
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
		requestMatchService.matchRejectedRequest(foundPictureGenerateRequest);
		return true;
	}

	@Transactional
	public PGRESUpdateByCreatorResponseDto submitToAdmin(Long userId, Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUserId(userId);

		if (!Objects.equals(foundPGRES.getCreator().getId(), foundCreator.getId())) {
			throw new ExpectedException(ErrorCode.NotAssignedToMe);
		}

		pictureService.findPictureCreatedByCreatorByPictureGenerateResponse(foundPGRES);

		foundPGRES.creatorSubmit();

		Duration elapsedDuration = foundPGRES.getElapsedTime();

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

		return PGRESUpdateByCreatorResponseDto.builder()
			.elapsedTime(getTimeString(elapsedDuration))
			.build();
	}

	@Transactional
	public PGRESUpdateByAdminResponseDto submitFinal(Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		List<PictureCompleted> pictureCompletedList = pictureService.findAllPictureCompletedByPictureGenerateResponse(
			foundPGRES);
		if (pictureCompletedList.isEmpty()) {
			throw new ExpectedException(ErrorCode.FinalPictureNotUploadedYet);
		}
		foundPGRES.adminSubmit();
		Duration elapsedDuration = foundPGRES.getElapsedTime();

		//TODO 어드민은 시간 체크 할 필요 없겠지?
		// edited at 2024-05-20
		// author 서병렬

		//TODO 요청자에게 앱 푸시알림
		// edited at 2024-05-21
		// author 서병렬

		return PGRESUpdateByAdminResponseDto.builder()
			.elapsedTime(getTimeString(elapsedDuration))
			.build();
	}

	private PictureGenerateResponse findPGRES(Long pictureGenerateResponseId) {
		return pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateResponseNotFound));
	}

	public Boolean updatePictureListCreatedByAdmin(Long userId, List<PictureUrlUpdateRequestDto> requestDtoList,
		Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(
				ErrorCode.PictureGenerateResponseNotFound));

		List<CreatePictureCompletedCommand> commandList = requestDtoList.stream().map(
			dto -> CreatePictureCompletedCommand.builder()
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.url(dto.getUrl())
				.userId(userId).build()
		).toList();
		pictureService.updatePictures(commandList);
		return true;
	}

	public List<PGREQDetailFindResponseDto> getPictureGenerateRequestDetail3(Long userId) {
		Creator foundCreator = findCreatorByUserId(userId);
		List<PictureGenerateRequestStatus> activeRequestStatusList = new ArrayList<>();
		activeRequestStatusList.add(PictureGenerateRequestStatus.IN_PROGRESS);

		List<PictureGenerateResponseStatus> activeResponseStatusList = new ArrayList<>();
		activeResponseStatusList.add(PictureGenerateResponseStatus.BEFORE_WORK);
		List<PictureGenerateRequest> foundPGREQList = pictureGenerateRequestRepository.findByCreatorAndActiveStatus(
			foundCreator, activeRequestStatusList, activeResponseStatusList);

		return foundPGREQList.stream().map(PGREQDetailFindResponseDto::new).toList();
	}
}

