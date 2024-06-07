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
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.common.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.dto.MemoUpdateRequestDto;
import com.gt.genti.dto.creator.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.admin.PGRESUpdateByAdminResponseDto;
import com.gt.genti.dto.creator.PGRESUpdateByCreatorResponseDto;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.util.TimeUtils;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.DepositRepository;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.SettlementRepository;
import com.gt.genti.repository.UserRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateWorkService {
	private final UserRepository userRepository;
	private final PictureService pictureService;
	private final CreatorRepository creatorRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final SettlementRepository settlementRepository;
	private final DepositRepository depositRepository;
	private final RequestMatchService requestMatchService;

	public PGREQBriefFindByCreatorResponseDto getPictureGenerateRequestBrief(Long user,
		PictureGenerateRequestStatus status) {
		Creator foundCreator = findCreatorByUserId(user);
		Optional<PictureGenerateRequest> foundPGR;
		switch (status) {
			case IN_PROGRESS ->
				foundPGR = pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
					foundCreator);
			case ASSIGNING ->
				foundPGR = pictureGenerateRequestRepository.findByStatusOrderByCreatedAtDesc(foundCreator, status);
			default -> throw new ExpectedException(DomainErrorCode.NotSupportedTemp);
		}

		return foundPGR.map(PGREQBriefFindByCreatorResponseDto::new)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));

	}

	public PGREQDetailFindResponseDto getPictureGenerateRequestDetail(Long user,
		Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUserId(user);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (foundPictureGenerateRequest.getCreator() == null) {
			throw new ExpectedException(DomainErrorCode.NotMatchedYet);
		} else if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw new ExpectedException(DomainErrorCode.NotAssignedToMe);
		}

		return new PGREQDetailFindResponseDto(foundPictureGenerateRequest);

	}

	public List<PGREQDetailFindResponseDto> getPictureGenerateRequestDetailAll(Long user) {
		Creator foundCreator = findCreatorByUserId(user);
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PGREQDetailFindResponseDto::new).toList();

	}

	@Transactional
	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<CommonPictureKeyUpdateRequestDto> commonPictureKeyUpdateRequestDtoList, Long uploaderId) {

		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);
		User foundUploader = findActiveUserByUserId(uploaderId);

		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = commonPictureKeyUpdateRequestDtoList.stream()
			.map(command -> (CreatePictureCreatedByCreatorCommand)CreatePictureCreatedByCreatorCommand.builder()
				.key(command.getKey())
				.uploader(foundUploader)
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.build()
			)
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

	public Boolean acceptPictureGenerateRequest(Long user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser().getId(), user)) {
			throw new ExpectedException(DomainErrorCode.NotAssignedToMe);
		}
		foundPictureGenerateRequest.accept();
		return true;
	}

	private Creator findCreatorByUserId(Long user) {
		return creatorRepository.findByUserId(user)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.CreatorNotFound));
	}

	private PictureGenerateRequest findPGREQ(Long pictureGenerateRequestId) {
		return pictureGenerateRequestRepository.findById(
				pictureGenerateRequestId)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));
	}

	@Transactional
	public Boolean rejectPictureGenerateRequest(Long user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser().getId(), user)) {
			throw new ExpectedException(DomainErrorCode.NotAssignedToMe);
		}

		foundPictureGenerateRequest.reject();
		requestMatchService.matchRejectedRequest(foundPictureGenerateRequest);
		return true;
	}

	@Transactional
	public PGRESUpdateByCreatorResponseDto submitToAdmin(Long user, Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUserId(user);

		if (!Objects.equals(foundPGRES.getCreator().getId(), foundCreator.getId())) {
			throw new ExpectedException(DomainErrorCode.NotAssignedToMe);
		}

		pictureService.findPictureCreatedByCreatorByPictureGenerateResponse(foundPGRES);

		foundPGRES.creatorSubmit();

		Duration elapsedDuration = foundPGRES.getElapsedTime();

		if (elapsedDuration.compareTo(Duration.ofHours(TimeUtils.PGRES_LIMIT_HOUR)) > 0) {
			throw new ExpectedException(DomainErrorCode.ExpiredPictureGenerateRequest);
		}

		Long reword = calculateReward(elapsedDuration.toMinutes());

		Settlement settlement = Settlement.builder()
			.pictureGenerateResponse(foundPGRES)
			.elapsed(elapsedDuration)
			.reward(reword).build();

		settlementRepository.save(settlement);
		Deposit foundDeposit = depositRepository.findByUserId(user)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.DepositNotFound));

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
			throw new ExpectedException(DomainErrorCode.FinalPictureNotUploadedYet);
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

	public Boolean updatePictureListCreatedByAdmin(Long uploaderId,
		List<CommonPictureKeyUpdateRequestDto> requestDtoList,
		Long pictureGenerateResponseId) {
		User foundUploader = findActiveUserByUserId(uploaderId);
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(
				DomainErrorCode.PictureGenerateResponseNotFound));

		List<CreatePictureCompletedCommand> commandList = requestDtoList.stream().map(
			dto -> (CreatePictureCompletedCommand)CreatePictureCompletedCommand.builder()
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.key(dto.getKey())
				.uploader(foundUploader).build()
		).toList();
		pictureService.updatePictures(commandList);
		return true;
	}

	public List<PGREQDetailFindResponseDto> getPictureGenerateRequestDetail3(Long user) {
		Creator foundCreator = findCreatorByUserId(user);
		List<PictureGenerateRequestStatus> activeRequestStatusList = new ArrayList<>();
		activeRequestStatusList.add(PictureGenerateRequestStatus.IN_PROGRESS);

		List<PictureGenerateResponseStatus> activeResponseStatusList = new ArrayList<>();
		activeResponseStatusList.add(PictureGenerateResponseStatus.BEFORE_WORK);
		List<PictureGenerateRequest> foundPGREQList = pictureGenerateRequestRepository.findByCreatorAndActiveStatus(
			foundCreator, activeRequestStatusList, activeResponseStatusList);

		return foundPGREQList.stream().map(PGREQDetailFindResponseDto::new).toList();
	}

	private User findActiveUserByUserId(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound));

		if (!foundUser.isActivate()) {
			throw new ExpectedException(DomainErrorCode.UserDeactivated);
		}
		return foundUser;
	}

	private PictureGenerateResponse findPGRES(Long pictureGenerateResponseId) {
		return pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.PictureGenerateResponseNotFound));
	}
}

