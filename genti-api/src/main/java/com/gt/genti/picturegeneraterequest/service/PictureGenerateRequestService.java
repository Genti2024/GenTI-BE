package com.gt.genti.picturegeneraterequest.service;

import static com.gt.genti.picturegeneraterequest.service.mapper.PictureGenerateRequestStatusForUser.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.firebase.FCMService;
import com.gt.genti.openai.dto.PromptAdvancementRequestCommand;
import com.gt.genti.openai.service.OpenAIService;
import com.gt.genti.picture.command.CreatePicturePoseCommand;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picture.pose.model.PicturePose;
import com.gt.genti.picture.service.PictureService;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQAdminMatchedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQCreatorSubmittedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.port.PictureGenerateRequestPort;
import com.gt.genti.picturegeneraterequest.service.mapper.PGREQStatusToPGREQStatusForUserMapper;
import com.gt.genti.picturegeneraterequest.service.mapper.PictureGenerateRequestStatusForUser;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESAdminMatchedDetailFindByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESCreatorSubmittedDetailFindByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESFindByUserResponseDto;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegenerateresponse.service.mapper.PGRESStatusToPGRESStatusForAdminMapper;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PictureGenerateRequestService implements PictureGenerateRequestUseCase {
	private final PictureGenerateRequestPort pictureGenerateRequestPort;
	private final OpenAIService openAIService;
	private final PictureService pictureService;
	private final RequestMatchService requestMatchService;
	private final UserRepository userRepository;
	private final PGRESStatusToPGRESStatusForAdminMapper pgresStatusToPGRESStatusForAdminMapper = new PGRESStatusToPGRESStatusForAdminMapper();
	private final FCMService notificationService;

	private final PGREQStatusToPGREQStatusForUserMapper pgreqStatusToPGREQStatusForUserMapper = new PGREQStatusToPGREQStatusForUserMapper();

	@Override
	public Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatched(Pageable pageable) {
		return pictureGenerateRequestPort.findByMatchToAdminIs(true, pageable)
			.map(convertPGREQToAdminMatchedResponseDto());
	}

	@Override
	public Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatchedByPGRESStatus(
		PictureGenerateResponseStatusForAdmin statusForAdmin, Pageable pageable) {
		return pictureGenerateRequestPort.findByPGRESStatusInAndMatchToAdminIs(
				pgresStatusToPGRESStatusForAdminMapper.clientToDb(statusForAdmin), true, pageable)
			.map(convertPGRESToAdminMatchedResponseDto());
	}

	@Override
	public Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatchedByRequesterEmail(String email,
		Pageable pageable) {
		User foundUser = userRepository.findByEmail(email)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFoundByEmail, email));
		return pictureGenerateRequestPort.findAllByRequester(foundUser, pageable).map(
			convertPGREQToAdminMatchedResponseDto());
	}

	@Override
	public Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto> getAllCreatorSubmitted(Pageable pageable) {
		return pictureGenerateRequestPort.findByMatchToAdminIs(false, pageable)
			.map(convertPGREQToCreatorSubmittedResponseDto());
	}

	@Override
	public Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto> getAllCreatorSubmittedByPGRESStatus(
		PictureGenerateResponseStatusForAdmin statusForAdmin, Pageable pageable) {
		return pictureGenerateRequestPort.findByPGRESStatusInAndMatchToAdminIs(
				pgresStatusToPGRESStatusForAdminMapper.clientToDb(statusForAdmin), false, pageable)
			.map(convertPGRESToCreatorSubmittedResponseDto());
	}

	@Override
	public Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto> getAllCreatorSubmittedByRequesterEmail(String email,
		Pageable pageable) {
		return null;
	}

	@Override
	public void cancelRequest(PictureGenerateRequest request, PictureGenerateRequestCancellationReason reason) {
		log.info("사진생성요청 id : {} 유저 email : {} 의 요청이 취소 처리 되었습니다.", request.getId(), request.getRequester().getEmail());
		request.canceled();
		User requester = request.getRequester();
		//TODO 삭제 이벤트를 요청자에게 FCM 푸시알림 보내기
		// edited at 2024-07-19
		// author 서병렬
		// notificationService.sendPushNoti(pgreq.getRequester().getId());

	}

	@Override
	public void cancelAllRequests(List<PictureGenerateRequest> requestList,
		PictureGenerateRequestCancellationReason reason) {
		requestList.forEach(PictureGenerateRequest::canceled);
	}

	@Override
	public Boolean confirmCanceledPGREQ(Long userId, Long pictureGenerateRequestId) {
		User foundUser = findUserById(userId);
		PictureGenerateRequest pgreq = pictureGenerateRequestPort.findById(pictureGenerateRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				String.format("id = %d", pictureGenerateRequestId)));

		if (!pgreq.getRequester().getId().equals(foundUser.getId())) {
			throw ExpectedException.withLogging(ResponseCode.OnlyRequesterCanViewPictureGenerateRequest);
		}
		pgreq.userConfirmedCancellation();
		return true;
	}

	@Override
	public List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(Long userId) {
		User foundUser = findUserById(userId);

		List<PGREQBriefFindByUserResponseDto> result = pictureGenerateRequestPort.findAllByRequester(
			foundUser).stream().map(
			PGREQBriefFindByUserResponseDto::new
		).sorted((dto1, dto2) -> dto2.getCreatedAt().compareTo(dto1.getCreatedAt())).toList();
		if (result.isEmpty()) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				"생성요청한 유저 id : " + foundUser.getId());
		}
		return result;
	}

	@Override
	public PGREQStatusResponseDto getPendingPGREQStatusIfExists(Long userId) {
		User foundUser = findUserById(userId);
		Optional<PictureGenerateRequest> optionalPGREQ = pictureGenerateRequestPort.findTopByRequesterOrderByCreatedAtDesc(
			foundUser);

		if (optionalPGREQ.isEmpty()) {
			return createPGREQStatusResponseDto(NEW_REQUEST_AVAILABLE, null, null);
		}

		PictureGenerateRequest foundPGREQ = optionalPGREQ.get();
		PictureGenerateRequestStatusForUser pgreqStatusForUser = pgreqStatusToPGREQStatusForUserMapper.dbToClient(
			foundPGREQ.getPictureGenerateRequestStatus()
		);

		return switch (pgreqStatusForUser) {
			case CANCELED -> createPGREQStatusResponseDto(CANCELED, foundPGREQ.getId(), null);
			case IN_PROGRESS -> createPGREQStatusResponseDto(IN_PROGRESS, foundPGREQ.getId(), null);
			case NEW_REQUEST_AVAILABLE -> createPGREQStatusResponseDto(NEW_REQUEST_AVAILABLE, null, null);
			case AWAIT_USER_VERIFICATION -> handleAwaitUserVerification(foundPGREQ);
		};
	}

	@Override
	public PictureGenerateRequest createPGREQ(Long userId,
		PGREQSaveCommand pgreqSaveCommand) {
		User foundUser = findUserById(userId);
		String posePictureKey = "";
		PicturePose foundPicturePose = null;
		if (!Objects.isNull(pgreqSaveCommand.getPosePictureKey())) {
			posePictureKey = pgreqSaveCommand.getPosePictureKey();
			String finalPosePictureKey = posePictureKey;
			foundPicturePose = pictureService.findByKeyPicturePose(posePictureKey)
				.orElseGet(() -> {
					log.info("""
						%s 유저가 요청에 포함한 포즈참고사진 key [%s] 기존 사진을 찾을 수 없어 신규 저장"""
						.formatted(foundUser.getEmail(), finalPosePictureKey));
					return pictureService.updatePicture(
						CreatePicturePoseCommand.builder()
							.key(finalPosePictureKey)
							.uploader(foundUser).build());
				});
		}

		List<String> facePictureUrl = pgreqSaveCommand.getFacePictureKeyList();
		List<PictureUserFace> uploadedFacePictureList = pictureService.updateIfNotExistsPictureUserFace(facePictureUrl,
			foundUser);

		String promptAdvanced = openAIService.getAdvancedPrompt(
			new PromptAdvancementRequestCommand(pgreqSaveCommand.getPrompt()));
		log.info(promptAdvanced);

		PictureGenerateRequest createdPGREQ = PictureGenerateRequest.builder()
			.requester(foundUser)
			.promptAdvanced(promptAdvanced)
			.prompt(pgreqSaveCommand.getPrompt())
			.shotCoverage(pgreqSaveCommand.getShotCoverage())
			.cameraAngle(pgreqSaveCommand.getCameraAngle())
			.pictureRatio(pgreqSaveCommand.getPictureRatio())
			.picturePose(foundPicturePose)
			.userFacePictureList(uploadedFacePictureList)
			.build();

		PictureGenerateRequest savedPGREQ = pictureGenerateRequestPort.save(createdPGREQ);
		requestMatchService.matchNewRequest(savedPGREQ);

		return savedPGREQ;
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
	}

	private PGREQStatusResponseDto createPGREQStatusResponseDto(
		PictureGenerateRequestStatusForUser status, Long pgreqId, PGRESFindByUserResponseDto pgresDto) {
		return PGREQStatusResponseDto.builder()
			.status(status)
			.pictureGenerateRequestId(pgreqId)
			.pgresFindByUserResponseDto(pgresDto)
			.build();
	}

	@Override
	public void modifyPGREQ(Long userId,
		Long pictureGenerateRequestId, PGREQSaveRequestDto pgreqSaveRequestDto) {
		User foundUser = findUserById(userId);
		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findByIdAndRequester(
				pictureGenerateRequestId, foundUser)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				String.format("생성요청한 유저 id : %d 사진생성요청 Id : %d", foundUser.getId(), pictureGenerateRequestId)));

		if (findPictureGenerateRequest.getCreator() != null) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestAlreadyInProgress);
		}

		PicturePose picturePose = findPictureGenerateRequest.getPicturePose();
		String givenPicturePoseKey = pgreqSaveRequestDto.getPosePicture().getKey();
		picturePose.modify(givenPicturePoseKey);

		List<PictureUserFace> pictureUserFaceList = findPictureGenerateRequest.getUserFacePictureList();
		List<String> givenPictureUserFaceKeyList = pgreqSaveRequestDto.getFacePictureList().stream().map(
			CommonPictureKeyUpdateRequestDto::getKey).toList();
		for (int i = 0; i < pictureUserFaceList.size(); i++) {
			String newKey = givenPictureUserFaceKeyList.get(i);
			pictureUserFaceList.get(i).modify(newKey);
		}

		findPictureGenerateRequest.modify(pgreqSaveRequestDto.getPrompt(), pgreqSaveRequestDto.getCameraAngle(),
			pgreqSaveRequestDto.getShotCoverage(), pgreqSaveRequestDto.getPictureRatio(), picturePose,
			pictureUserFaceList);
	}

	private PGREQStatusResponseDto handleAwaitUserVerification(PictureGenerateRequest foundPGREQ) {
		PictureGenerateResponse needVerifyPGRES = foundPGREQ.getResponseList().stream()
			.filter(pgres -> pgres.getStatus().equals(PictureGenerateResponseStatus.ADMIN_SUBMITTED_FINAL))
			.findFirst()
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UnHandledException,
				String.format(
					"No suitable picture generation response found for verification. Request ID: [%d], Status: [%s]",
					foundPGREQ.getId(), foundPGREQ.getPictureGenerateRequestStatus())));

		return createPGREQStatusResponseDto(AWAIT_USER_VERIFICATION, foundPGREQ.getId(),
			new PGRESFindByUserResponseDto(needVerifyPGRES));
	}

	@NotNull
	private Function<PictureGenerateResponse, PGREQAdminMatchedDetailFindByAdminResponseDto> convertPGRESToAdminMatchedResponseDto() {
		return pgres -> {
			PictureGenerateRequest pgreq = pgres.getRequest();
			List<PGRESAdminMatchedDetailFindByAdminResponseDto> responseList = List.of(
				PGRESAdminMatchedDetailFindByAdminResponseDto.builder()
					.pictureGenerateResponseId(pgres.getId())
					.memo(pgres.getMemo())
					.pictureCompletedList(
						pgres.getCompletedPictureList().stream().map(CommonPictureResponseDto::of).toList())
					.status(pgresStatusToPGRESStatusForAdminMapper.dbToClient(pgres.getStatus()))
					.adminInCharge(pgres.getAdminInCharge())
					.build());
			return buildAdminMatchedPGREQDetail(pgreq, responseList);
		};
	}

	@NotNull
	private Function<PictureGenerateRequest, PGREQAdminMatchedDetailFindByAdminResponseDto> convertPGREQToAdminMatchedResponseDto() {
		return pgreq -> {
			List<PGRESAdminMatchedDetailFindByAdminResponseDto> responseList = pgreq.getResponseList()
				.stream()
				.map(res -> PGRESAdminMatchedDetailFindByAdminResponseDto.builder()
					.pictureGenerateResponseId(res.getId())
					.memo(res.getMemo())
					.pictureCompletedList(
						res.getCompletedPictureList().stream().map(CommonPictureResponseDto::of).toList())
					.status(pgresStatusToPGRESStatusForAdminMapper.dbToClient(res.getStatus()))
					.adminInCharge(res.getAdminInCharge())
					.build())
				.toList();
			return buildAdminMatchedPGREQDetail(pgreq, responseList);
		};
	}

	private PGREQAdminMatchedDetailFindByAdminResponseDto buildAdminMatchedPGREQDetail(PictureGenerateRequest pgreq,
		List<PGRESAdminMatchedDetailFindByAdminResponseDto> responseList) {
		return PGREQAdminMatchedDetailFindByAdminResponseDto.builder()
			.posePicture(CommonPictureResponseDto.of(pgreq.getPicturePose()))
			.cameraAngle(pgreq.getCameraAngle())
			.facePictureList(
				pgreq.getUserFacePictureList().stream().map(CommonPictureResponseDto::of).toList())
			.requesterEmail(pgreq.getRequester().getEmail())
			.prompt(pgreq.getPrompt())
			.promptAdvanced(pgreq.getPromptAdvanced())
			.createdAt(pgreq.getCreatedAt())
			.responseList(responseList)
			.pictureGenerateRequestId(pgreq.getId())
			.sex(pgreq.getRequester().getSex())
			.shotCoverage(pgreq.getShotCoverage())
			.build();
	}

	private PGREQCreatorSubmittedDetailFindByAdminResponseDto buildCreatorSubmittedPGREQDetail(
		PictureGenerateRequest pgreq,
		List<PGRESCreatorSubmittedDetailFindByAdminResponseDto> responseList) {
		return PGREQCreatorSubmittedDetailFindByAdminResponseDto.builder()
			.posePicture(CommonPictureResponseDto.of(pgreq.getPicturePose()))
			.cameraAngle(pgreq.getCameraAngle())
			.facePictureList(
				pgreq.getUserFacePictureList().stream().map(CommonPictureResponseDto::of).toList())
			.requesterEmail(pgreq.getRequester().getEmail())
			.prompt(pgreq.getPrompt())
			.promptAdvanced(pgreq.getPromptAdvanced())
			.createdAt(pgreq.getCreatedAt())
			.responseList(responseList)
			.pictureGenerateRequestId(pgreq.getId())
			.shotCoverage(pgreq.getShotCoverage())
			.build();
	}

	private Function<? super PictureGenerateRequest, ? extends PGREQCreatorSubmittedDetailFindByAdminResponseDto> convertPGREQToCreatorSubmittedResponseDto() {
		return pgreq -> {
			List<PGRESCreatorSubmittedDetailFindByAdminResponseDto> responseList = pgreq.getResponseList()
				.stream()
				.map(res -> PGRESCreatorSubmittedDetailFindByAdminResponseDto.builder()
					.pictureGenerateResponseId(res.getId())
					.memo(res.getMemo())
					.pictureCreatedByCreator(
						res.getCreatedByCreatorPictureList().stream().map(CommonPictureResponseDto::of).toList())
					.pictureCompletedList(
						res.getCompletedPictureList().stream().map(CommonPictureResponseDto::of).toList())
					.responseStatus(pgresStatusToPGRESStatusForAdminMapper.dbToClient(res.getStatus()))
					.submittedByCreatorAt(res.getSubmittedByCreatorAt())
					.build())
				.toList();
			return buildCreatorSubmittedPGREQDetail(pgreq, responseList);
		};
	}

	private Function<? super PictureGenerateResponse, ? extends PGREQCreatorSubmittedDetailFindByAdminResponseDto> convertPGRESToCreatorSubmittedResponseDto() {
		return res -> {
			PictureGenerateRequest pgreq = res.getRequest();
			List<PGRESCreatorSubmittedDetailFindByAdminResponseDto> responseList = List.of(
				PGRESCreatorSubmittedDetailFindByAdminResponseDto.builder()
					.pictureGenerateResponseId(res.getId())
					.memo(res.getMemo())
					.pictureCreatedByCreator(
						res.getCreatedByCreatorPictureList().stream().map(CommonPictureResponseDto::of).toList())
					.pictureCompletedList(
						res.getCompletedPictureList().stream().map(CommonPictureResponseDto::of).toList())
					.responseStatus(pgresStatusToPGRESStatusForAdminMapper.dbToClient(res.getStatus()))
					.submittedByCreatorAt(res.getSubmittedByCreatorAt())
					.build());
			return buildCreatorSubmittedPGREQDetail(pgreq, responseList);
		};
	}
}
