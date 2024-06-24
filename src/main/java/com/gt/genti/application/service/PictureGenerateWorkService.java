package com.gt.genti.application.service;

import static com.gt.genti.domain.enums.converter.db.EnumUtil.*;
import static com.gt.genti.error.ResponseCode.*;
import static com.gt.genti.other.util.DateTimeUtils.*;

import java.time.Duration;
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
import com.gt.genti.dto.admin.request.PGRESUpdateAdminInChargeRequestDto;
import com.gt.genti.dto.admin.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.dto.admin.response.PGRESSubmitByAdminResponseDto;
import com.gt.genti.dto.admin.response.PGRESUpdateAdminInChargeResponseDto;
import com.gt.genti.dto.common.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.dto.creator.request.MemoUpdateRequestDto;
import com.gt.genti.dto.creator.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.dto.creator.response.PGRESSubmitByCreatorResponseDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.util.DateTimeUtils;
import com.gt.genti.other.util.PictureEntityUtils;
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

	public PGREQBriefFindByCreatorResponseDto getPictureGenerateRequestBrief(User user,
		PictureGenerateRequestStatus status) {

		Creator foundCreator = findCreatorByUser(user);
		Optional<PictureGenerateRequest> foundPGR;
		switch (status) {
			case IN_PROGRESS ->
				foundPGR = pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
					foundCreator);
			case ASSIGNING ->
				foundPGR = pictureGenerateRequestRepository.findByStatusOrderByCreatedAtDesc(foundCreator, status);
			default -> throw ExpectedException.withLogging(UnHandledException,
				"조회불가능한 사진생성요청 상태 Status =" + status.getStringValue());
		}

		return foundPGR.map(PGREQBriefFindByCreatorResponseDto::new)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateRequestNotFound,
				String.format("공급자 id : %d 사진생성요청의 진행상태 : %s", foundCreator.getId(), status.getStringValue())));

	}

	public PGREQDetailFindByAdminResponseDto getPictureGenerateRequestDetail(User user,
		Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUser(user);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (foundPictureGenerateRequest.getCreator() == null) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotAssignedToCreator);
		} else if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotAssignedToCreator);
		}

		return new PGREQDetailFindByAdminResponseDto(foundPictureGenerateRequest);

	}

	public List<PGREQDetailFindByAdminResponseDto> getPictureGenerateRequestDetailAll(User user) {
		Creator foundCreator = findCreatorByUser(user);
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PGREQDetailFindByAdminResponseDto::new).toList();

	}

	@Transactional
	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<CommonPictureKeyUpdateRequestDto> commonPictureKeyUpdateRequestDtoList, User user) {
		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUser(user);
		if (!Objects.equals(foundPictureGenerateResponse.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotAssignedToCreator);
		}
		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = commonPictureKeyUpdateRequestDtoList.stream()
			.map(command -> (CreatePictureCreatedByCreatorCommand)CreatePictureCreatedByCreatorCommand.builder()
				.key(command.getKey())
				.uploader(user)
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

	@Transactional
	public Boolean acceptPictureGenerateRequest(User user, Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUser(user);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);

		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotAssignedToCreator);
		}

		foundPictureGenerateRequest.acceptByCreator();
		PictureGenerateResponse newPGRES = new PictureGenerateResponse(foundCreator, foundPictureGenerateRequest);
		pictureGenerateResponseRepository.save(newPGRES);

		return true;
	}

	private PictureGenerateRequest findPGREQ(Long pictureGenerateRequestId) {
		return pictureGenerateRequestRepository.findById(
				pictureGenerateRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateRequestNotFound,
				String.format("사진생성요청 Id : %d", pictureGenerateRequestId)));
	}

	@Transactional
	public Boolean rejectPictureGenerateRequest(User user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser(), user)) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotAssignedToCreator);
		}

		foundPictureGenerateRequest.rejectByCreator();
		requestMatchService.matchRejectedRequest(foundPictureGenerateRequest);
		return true;
	}

	@Transactional
	public PGRESSubmitByCreatorResponseDto submitToAdmin(User user, Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUser(user);

		if (!Objects.equals(foundPGRES.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotAssignedToCreator);
		}
		pictureService.findPictureCreatedByCreatorByPictureGenerateResponse(foundPGRES);

		Duration elapsedDuration = foundPGRES.creatorSubmitAndGetElapsedTime();
		if (elapsedDuration.compareTo(Duration.ofHours(DateTimeUtils.PGRES_LIMIT_HOUR)) > 0) {
			throw ExpectedException.withLogging(SubmitBlockedDueToPictureGenerateResponseIsExpired);
		}
		Long reward = calculateReward(elapsedDuration.toMinutes());

		createSettlementAndDeposit(foundPGRES, elapsedDuration, reward, foundCreator);

		return PGRESSubmitByCreatorResponseDto.builder()
			.elapsedTime(getTimeString(elapsedDuration))
			.reward(reward)
			.build();
	}

	private void createSettlementAndDeposit(PictureGenerateResponse foundPGRES, Duration elapsedDuration, Long reward,
		Creator foundCreator) {
		Settlement settlement = Settlement.builder()
			.pictureGenerateResponse(foundPGRES)
			.elapsedMinutes(elapsedDuration.toMinutes())
			.reward(reward)
			.build();

		settlementRepository.save(settlement);

		Deposit foundDeposit = depositRepository.findByCreator(foundCreator)
			.orElseThrow(() -> ExpectedException.withLogging(DepositNotFound));
		foundDeposit.add(reward);
		foundCreator.completeTask();
	}

	@Transactional
	public PGRESSubmitByAdminResponseDto submitFinal(Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		List<PictureCompleted> pictureCompletedList = pictureService.findAllPictureCompletedByPictureGenerateResponse(
			foundPGRES);
		if (pictureCompletedList.isEmpty()) {
			throw ExpectedException.withLogging(FinalPictureNotUploadedYet);
		}
		foundPGRES.adminSubmit();
		Duration elapsedDuration = foundPGRES.getAdminElapsedTime();

		//TODO 어드민은 시간 체크 할 필요 없겠지?
		// edited at 2024-05-20
		// author 서병렬

		//TODO 요청자에게 앱 푸시알림
		// edited at 2024-05-21
		// author 서병렬

		return PGRESSubmitByAdminResponseDto.builder()
			.pictureGenerateResponseId(foundPGRES.getId())
			.elapsedTime(getTimeString(elapsedDuration))
			.build();
	}

	public List<CommonPictureResponseDto> updatePictureListCreatedByAdmin(User uploader,
		List<CommonPictureKeyUpdateRequestDto> requestDtoList,
		Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(
				PictureGenerateResponseNotFound));

		if (PictureGenerateResponseStatus.COMPLETED.equals(foundPGRES.getStatus())) {
			throw ExpectedException.withLogging(AlreadyCompletedPictureGenerateResponse);
		}

		List<CreatePictureCompletedCommand> commandList = requestDtoList.stream().map(
			dto -> (CreatePictureCompletedCommand)CreatePictureCompletedCommand.builder()
				.pictureGenerateResponse(foundPGRES)
				.key(dto.getKey())
				.requester(foundPGRES.getRequest().getRequester())
				.uploader(uploader)
				.pictureRatio(foundPGRES.getRequest().getPictureRatio())
				.build()
		).toList();
		return pictureService.updatePictures(commandList)
			.stream().map(PictureEntityUtils::toCommonResponse)
			.toList();
	}

	public List<PGREQDetailFindByAdminResponseDto> getPictureGenerateRequestDetail3(User user) {
		Creator foundCreator = findCreatorByUser(user);

		List<PictureGenerateRequest> foundPGREQList = pictureGenerateRequestRepository.findByCreatorAndActiveStatus(
			foundCreator, PGREQ_IN_PROGRESS_LIST, IN_PROGRESS_PGRES_FOR_CREATOR);

		return foundPGREQList.stream().map(PGREQDetailFindByAdminResponseDto::new).toList();
	}

	private PictureGenerateResponse findPGRES(Long pictureGenerateResponseId) {
		return pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateResponseNotFound));
	}

	private Creator findCreatorByUser(User user) {
		return creatorRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(CreatorNotFound, user.getId().toString()));
	}

	@Transactional
	public PGRESUpdateAdminInChargeResponseDto updateAdminInCharge(Long pgresId,
		PGRESUpdateAdminInChargeRequestDto requestDto) {
		PictureGenerateResponse foundPGRES = findPGRES(pgresId);

		if (!PGRES_CAN_CHANGE_ADMIN_IN_CHARGE_LIST.contains(foundPGRES.getStatus())) {
			throw ExpectedException.withLogging(RequestBlockedDueToPictureGenerateResponseStatus,
				foundPGRES.getStatus().getResponse());
		}
		foundPGRES.updateInChargeAdmin(requestDto.getAdminInCharge());
		return PGRESUpdateAdminInChargeResponseDto.builder()
			.pictureGenerateResponseId(foundPGRES.getId())
			.adminInCharge(foundPGRES.getAdminInCharge())
			.status(foundPGRES.getStatus())
			.build();
	}
}

