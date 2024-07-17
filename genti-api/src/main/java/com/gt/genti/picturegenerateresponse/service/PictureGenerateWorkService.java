package com.gt.genti.picturegenerateresponse.service;

import static com.gt.genti.common.EnumUtil.*;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.deposit.repository.DepositRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.command.CreatePictureCompletedCommand;
import com.gt.genti.picture.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picture.service.PictureService;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegeneraterequest.service.RequestMatchService;
import com.gt.genti.picturegenerateresponse.dto.request.MemoUpdateRequestDto;
import com.gt.genti.picturegenerateresponse.dto.request.PGRESUpdateAdminInChargeRequestDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESSubmitByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESSubmitByCreatorResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESUpdateAdminInChargeResponseDto;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.settlement.model.Settlement;
import com.gt.genti.settlement.repository.SettlementRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.util.DateTimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureGenerateWorkService {
	private final PictureService pictureService;
	private final CreatorRepository creatorRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final SettlementRepository settlementRepository;
	private final DepositRepository depositRepository;
	private final RequestMatchService requestMatchService;
	private final UserRepository userRepository;

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
			default -> throw ExpectedException.withLogging(ResponseCode.UnHandledException,
				"조회불가능한 사진생성요청 상태 Status =" + status.getStringValue());
		}

		return foundPGR.map(PGREQBriefFindByCreatorResponseDto::new)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				String.format("공급자 id : %d 사진생성요청의 진행상태 : %s", foundCreator.getId(), status.getStringValue())));

	}

	public PGREQDetailFindByAdminResponseDto getPictureGenerateRequestDetail(Long userId,
		Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUserId(userId);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (foundPictureGenerateRequest.getCreator() == null) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotAssignedToCreator);
		} else if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotAssignedToCreator);
		}

		return new PGREQDetailFindByAdminResponseDto(foundPictureGenerateRequest);

	}

	public List<PGREQDetailFindByAdminResponseDto> getPictureGenerateRequestDetailAll(Long userId) {
		Creator foundCreator = findCreatorByUserId(userId);
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PGREQDetailFindByAdminResponseDto::new).toList();

	}

	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<CommonPictureKeyUpdateRequestDto> commonPictureKeyUpdateRequestDtoList, Long userId) {
		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUserId(userId);

		if (!Objects.equals(foundPictureGenerateResponse.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotAssignedToCreator);
		}
		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = commonPictureKeyUpdateRequestDtoList.stream()
			.map(command -> (CreatePictureCreatedByCreatorCommand)CreatePictureCreatedByCreatorCommand.builder()
				.key(command.getKey())
				.uploader(foundCreator.getUser())
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.build()
			)
			.toList();

		pictureService.updateAll(newUploadPictures);
		return true;
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
	}

	
	public Boolean updateMemo(Long pictureGenerateResponseId, MemoUpdateRequestDto memoUpdateRequestDto) {
		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);
		foundPictureGenerateResponse.updateMemo(memoUpdateRequestDto.getMemo());
		return true;
	}

	
	public Boolean acceptPictureGenerateRequest(Long userId, Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUserId(userId);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);

		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotAssignedToCreator);
		}

		foundPictureGenerateRequest.acceptByCreator();
		PictureGenerateResponse newPGRES = new PictureGenerateResponse(foundCreator, foundPictureGenerateRequest);
		pictureGenerateResponseRepository.save(newPGRES);

		return true;
	}

	private PictureGenerateRequest findPGREQ(Long pictureGenerateRequestId) {
		return pictureGenerateRequestRepository.findById(
				pictureGenerateRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				String.format("사진생성요청 Id : %d", pictureGenerateRequestId)));
	}

	public Boolean rejectPictureGenerateRequest(Long userId, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser().getId(), userId)) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotAssignedToCreator);
		}

		foundPictureGenerateRequest.rejectByCreator();
		requestMatchService.matchRejectedRequest(foundPictureGenerateRequest);
		return true;
	}

	
	public PGRESSubmitByCreatorResponseDto submitToAdmin(Long userId, Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUserId(userId);

		if (!Objects.equals(foundPGRES.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotAssignedToCreator);
		}
		pictureService.findPictureCreatedByCreatorByPictureGenerateResponse(foundPGRES);

		Duration elapsedDuration = foundPGRES.creatorSubmitAndGetElapsedTime();
		if (elapsedDuration.compareTo(Duration.ofHours(DateTimeUtil.PGRES_LIMIT_HOUR)) > 0) {
			throw ExpectedException.withLogging(ResponseCode.SubmitBlockedDueToPictureGenerateResponseIsExpired);
		}
		Long reward = DateTimeUtil.calculateReward(elapsedDuration.toMinutes());

		createSettlementAndDeposit(foundPGRES, elapsedDuration, reward, foundCreator);

		return PGRESSubmitByCreatorResponseDto.builder()
			.elapsedTime(DateTimeUtil.getTimeString(elapsedDuration))
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
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.DepositNotFound));
		foundDeposit.add(reward);
		foundCreator.completeTask();
	}

	
	public PGRESSubmitByAdminResponseDto submitFinal(Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		List<PictureCompleted> pictureCompletedList = pictureService.findAllPictureCompletedByPictureGenerateResponse(
			foundPGRES);
		if (pictureCompletedList.isEmpty()) {
			throw ExpectedException.withLogging(ResponseCode.FinalPictureNotUploadedYet, pictureGenerateResponseId);
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
			.elapsedTime(DateTimeUtil.getTimeString(elapsedDuration))
			.build();
	}

	public List<CommonPictureResponseDto> updatePictureListCreatedByAdmin(Long userId,
		List<CommonPictureKeyUpdateRequestDto> requestDtoList,
		Long pictureGenerateResponseId) {

		User foundUser = findUserById(userId);
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(
				ResponseCode.PictureGenerateResponseNotFound));

		if (PictureGenerateResponseStatus.COMPLETED.equals(foundPGRES.getStatus())) {
			throw ExpectedException.withLogging(ResponseCode.AlreadyCompletedPictureGenerateResponse);
		}

		List<CreatePictureCompletedCommand> commandList = requestDtoList.stream().map(
			dto -> (CreatePictureCompletedCommand)CreatePictureCompletedCommand.builder()
				.pictureGenerateResponse(foundPGRES)
				.key(dto.getKey())
				.requester(foundPGRES.getRequest().getRequester())
				.uploader(foundUser)
				.pictureRatio(foundPGRES.getRequest().getPictureRatio())
				.build()
		).toList();
		return pictureService.updatePictures(commandList)
			.stream().map(CommonPictureResponseDto::of)
			.toList();
	}

	public List<PGREQDetailFindByAdminResponseDto> getPictureGenerateRequestDetail3(Long userId) {
		Creator foundCreator = findCreatorByUserId(userId);

		List<PictureGenerateRequest> foundPGREQList = pictureGenerateRequestRepository.findByCreatorAndActiveStatus(
			foundCreator, PGREQ_IN_PROGRESS_LIST, IN_PROGRESS_PGRES_FOR_CREATOR);

		return foundPGREQList.stream().map(PGREQDetailFindByAdminResponseDto::new).toList();
	}

	private PictureGenerateResponse findPGRES(Long pictureGenerateResponseId) {
		return pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateResponseNotFound));
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CreatorNotFound, userId));
	}

	public PGRESUpdateAdminInChargeResponseDto updateAdminInCharge(Long pgresId,
		PGRESUpdateAdminInChargeRequestDto requestDto) {
		PictureGenerateResponse foundPGRES = findPGRES(pgresId);

		if (!PGRES_CAN_CHANGE_ADMIN_IN_CHARGE_LIST.contains(foundPGRES.getStatus())) {
			throw ExpectedException.withLogging(ResponseCode.RequestBlockedDueToPictureGenerateResponseStatus,
				foundPGRES.getStatus().getResponse());
		}
		foundPGRES.updateInChargeAdmin(requestDto.getAdminInCharge());
		return PGRESUpdateAdminInChargeResponseDto.builder()
			.pictureGenerateResponseId(foundPGRES.getId())
			.adminInCharge(foundPGRES.getAdminInCharge())
			.status(foundPGRES.getStatus())
			.build();
	}

	public Boolean verifyPGRES(Long userId, Long pgresId) {
		User foundUser = findUserById(userId);
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(pgresId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateResponseNotFound));
		PictureGenerateRequest pgreq = foundPGRES.getRequest();
		if (!Objects.equals(pgreq.getRequester().getId(), foundUser.getId())) {
			throw ExpectedException.withLogging(ResponseCode.OnlyRequesterCanViewPictureGenerateRequest);
		}
		if (!pgreq.getPictureGenerateRequestStatus().equals(PictureGenerateRequestStatus.AWAIT_USER_VERIFICATION)) {
			throw ExpectedException.withLogging(ResponseCode.UnexpectedPictureGenerateRequestStatus,
				pgreq.getPictureGenerateRequestStatus().getResponse());
		}
		pgreq.userVerified();
		foundPGRES.userVerified();

		return true;
	}
}

