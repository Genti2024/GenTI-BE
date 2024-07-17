package com.gt.genti.picturegeneraterequest.service;

import static com.gt.genti.common.EnumUtil.*;
import static com.gt.genti.picturegeneraterequest.dto.response.PictureGenerateRequestStatusForUser.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
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
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PictureGenerateRequestStatusForUser;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.port.PictureGenerateRequestPort;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESDetailFindByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESFindByUserResponseDto;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
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

	@Override
	public Page<PGREQDetailFindByAdminResponseDto> getAllByMatchToAdminIs(boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestPort.findByMatchToAdminIs(matchToAdmin, pageable)
			.map(convertPGREQToResponseDto());
	}

	public Page<PGREQDetailFindByAdminResponseDto> getAllByPGRESStatusInAndMatchToAdminIs(
		List<PictureGenerateResponseStatus> statusList, boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestPort.findByPGRESStatusInAndMatchToAdminIs(statusList, matchToAdmin, pageable)
			.map(convertPGRESToResponseDto());
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
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
		Optional<PictureGenerateRequest> optionalPGREQ = pictureGenerateRequestPort.findByUserAndStatusInOrderByCreatedByDesc(
			foundUser, PGREQ_PENDING_LIST);
		if (optionalPGREQ.isPresent()) {
			PictureGenerateRequest foundPGREQ = optionalPGREQ.get();
			PictureGenerateRequestStatusForUser statusForUser;
			switch (foundPGREQ.getPictureGenerateRequestStatus()) {
				case AWAIT_USER_VERIFICATION -> statusForUser = AWAIT_USER_VERIFICATION;
				case CANCELED -> statusForUser = CANCELED;
				case COMPLETED -> statusForUser = COMPLETED;
				default -> statusForUser = IN_PROGRESS;
			}
			if (statusForUser == AWAIT_USER_VERIFICATION) {
				PictureGenerateResponse needVerifyPGRES = foundPGREQ.getResponseList()
					.stream()
					.filter(pgres -> pgres.getStatus().equals(PictureGenerateResponseStatus.SUBMITTED_FINAL))
					.findFirst()
					.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UnHandledException,
						String.format("유저가 최종 확인할 적절한 사진생성응답을 찾지 못했습니다. \n 사진생성요청 id [%d] 사진생성요청 상태 [%s]"
							, foundPGREQ.getId(), foundPGREQ.getPictureGenerateRequestStatus())));
				return PGREQStatusResponseDto.builder()
					.pictureGenerateRequestId(foundPGREQ.getId())
					.status(statusForUser)
					.pgresFindByUserResponseDto(new PGRESFindByUserResponseDto(needVerifyPGRES))
					.build();
			}
			return PGREQStatusResponseDto.builder()
				.pictureGenerateRequestId(foundPGREQ.getId())
				.status(statusForUser)
				.build();
		}
		throw ExpectedException.withoutLogging(ResponseCode.PictureGenerateRequestNotFound,
			"사진생성요청 상태 in " + PGREQ_PENDING_LIST.stream().map(
				PictureGenerateRequestStatus::getStringValue).collect(Collectors.joining(", ")));
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
		requestMatchService.matchNewRequest(createdPGREQ);
		return pictureGenerateRequestPort.save(createdPGREQ);
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

	@NotNull
	private Function<PictureGenerateResponse, PGREQDetailFindByAdminResponseDto> convertPGRESToResponseDto() {
		return pgres -> {
			PictureGenerateRequest pgreq = pgres.getRequest();
			List<PGRESDetailFindByAdminResponseDto> responseList = List.of(
				new PGRESDetailFindByAdminResponseDto(pgres));
			return buildPGREQDetail(pgreq, responseList);
		};
	}

	private Function<PictureGenerateRequest, PGREQDetailFindByAdminResponseDto> convertPGREQToResponseDto() {
		return pgreq -> {
			List<PGRESDetailFindByAdminResponseDto> responseList = pgreq.getResponseList()
				.stream()
				.map(PGRESDetailFindByAdminResponseDto::new)
				.toList();
			return buildPGREQDetail(pgreq, responseList);
		};
	}

	private PGREQDetailFindByAdminResponseDto buildPGREQDetail(PictureGenerateRequest pgreq,
		List<PGRESDetailFindByAdminResponseDto> responseList) {
		return PGREQDetailFindByAdminResponseDto.builder()
			.posePicture(CommonPictureResponseDto.of(pgreq.getPicturePose()))
			.requesterId(pgreq.getRequester().getId())
			.requestStatus(pgreq.getPictureGenerateRequestStatus())
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
}
