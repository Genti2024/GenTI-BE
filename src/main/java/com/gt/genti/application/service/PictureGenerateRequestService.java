package com.gt.genti.application.service;

import static com.gt.genti.domain.enums.PictureGenerateRequestStatus.*;
import static com.gt.genti.domain.enums.converter.db.EnumUtil.*;
import static com.gt.genti.error.ResponseCode.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.adapter.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.application.port.in.PictureGenerateRequestPort;
import com.gt.genti.command.CreatePicturePoseCommand;
import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.admin.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.dto.admin.response.PGRESDetailFindByAdminResponseDto;
import com.gt.genti.dto.common.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQStatusResponseDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.external.openai.dto.PromptAdvancementRequestCommand;
import com.gt.genti.external.openai.service.OpenAIService;
import com.gt.genti.other.util.PictureEntityUtils;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureGenerateRequestService implements PictureGenerateRequestUseCase {
	private final PictureGenerateRequestPort pictureGenerateRequestPort;
	private final OpenAIService openAIService;
	private final PictureService pictureService;
	private final RequestMatchService requestMatchService;

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

	@Override
	public PGREQBriefFindByUserResponseDto getByRequesterAndStatusIs(User requester,
		PictureGenerateRequestStatus status) {
		return pictureGenerateRequestPort.findByRequesterAndStatusInOrderByCreatedByDesc(requester, List.of(status))
			.map(PGREQBriefFindByUserResponseDto::new)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateRequestNotFound,
				"사진생성요청 상태 = " + status.getStringValue()));
	}

	@Override
	public List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(User user) {
		List<PGREQBriefFindByUserResponseDto> result = pictureGenerateRequestPort.findAllByRequester(
			user).stream().map(
			PGREQBriefFindByUserResponseDto::new
		).sorted((dto1, dto2) -> dto2.getCreatedAt().compareTo(dto1.getCreatedAt())).toList();
		if (result.isEmpty()) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotFound, "생성요청한 유저 id : " + user.getId());
		}
		return result;
	}

	@Override
	public PGREQStatusResponseDto getPGREQStatusIfPendingExists(User user) {
		Optional<PictureGenerateRequest> optionalPGREQ = pictureGenerateRequestPort.findByRequesterAndStatusInOrderByCreatedByDesc(
			user, PGREQ_PENDING_LIST);
		if (optionalPGREQ.isPresent()) {
			PictureGenerateRequest foundPGREQ = optionalPGREQ.get();
			return PGREQStatusResponseDto.builder()
				.pictureGenerateRequestId(foundPGREQ.getId())
				.status(foundPGREQ.getPictureGenerateRequestStatus())
				.build();
		}
		throw ExpectedException.withoutLogging(PictureGenerateRequestNotFound,
			"사진생성요청 상태 in " + PGREQ_PENDING_LIST.stream().map(
				PictureGenerateRequestStatus::getStringValue).collect(Collectors.joining(", ")));
	}

	@Override
	public PGREQDetailFindByUserResponseDto findPGREQByRequestAndId(User user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findById(
				pictureGenerateRequestId)
			.orElseThrow(() ->
				ExpectedException.withLogging(PictureGenerateRequestNotFound,
					"사진생성요청 Id : " + pictureGenerateRequestId));
		if (!Objects.equals(foundPictureGenerateRequest.getRequester().getId(), user.getId())) {
			throw ExpectedException.withLogging(PictureGenerateRequestVisibilityRestrictedToRequester);
		}
		return new PGREQDetailFindByUserResponseDto(foundPictureGenerateRequest);
	}

	@Transactional
	@Override
	public PictureGenerateRequest createPGREQ(User requester,
		PGREQSaveCommand pgreqSaveCommand) {

		String posePictureKey = "";
		PicturePose foundPicturePose = null;
		if (!Objects.isNull(pgreqSaveCommand.getPosePictureKey())) {
			posePictureKey = pgreqSaveCommand.getPosePictureKey();
			String finalPosePictureKey = posePictureKey;
			foundPicturePose = pictureService.findByKeyPicturePose(posePictureKey)
				.orElseGet(() -> {
					log.info("""
						%s 유저가 요청에 포함한 포즈참고사진 key [%s] 기존 사진을 찾을 수 없어 신규 저장"""
						.formatted(requester.getEmail(), finalPosePictureKey));
					return pictureService.updatePicture(
						CreatePicturePoseCommand.builder()
							.key(finalPosePictureKey)
							.uploader(requester).build());
				});
		}

		List<String> facePictureUrl = pgreqSaveCommand.getFacePictureKeyList();
		List<PictureUserFace> uploadedFacePictureList = pictureService.updateIfNotExistsPictureUserFace(facePictureUrl,
			requester);

		String promptAdvanced = openAIService.getAdvancedPrompt(
			new PromptAdvancementRequestCommand(pgreqSaveCommand.getPrompt()));
		log.info(promptAdvanced);

		PictureGenerateRequest pgr = PictureGenerateRequest.builder()
			.requester(requester)
			.promptAdvanced(promptAdvanced)
			.pgreqSaveCommand(pgreqSaveCommand)
			.picturePose(foundPicturePose)
			.userFacePictureList(uploadedFacePictureList)
			.build();
		requestMatchService.matchNewRequest(pgr);
		return pictureGenerateRequestPort.save(pgr);
	}

	@Transactional
	@Override
	public void modifyPGREQ(User requester,
		Long pictureGenerateRequestId, PGREQSaveRequestDto pgreqSaveRequestDto) {

		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findByIdAndRequester(
				pictureGenerateRequestId, requester)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateRequestNotFound,
				String.format("생성요청한 유저 id : %d 사진생성요청 Id : %d", requester.getId(), pictureGenerateRequestId)));

		if (findPictureGenerateRequest.getCreator() != null) {
			throw ExpectedException.withLogging(PictureGenerateRequestAlreadyInProgress);
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

		findPictureGenerateRequest.modify(pgreqSaveRequestDto, picturePose, pictureUserFaceList);
	}

	@Transactional
	@Override
	public Boolean verifyCompletedPGREQ(User requester, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByIdAndRequester(
				pictureGenerateRequestId, requester)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateRequestNotFound,
				String.format("생성요청한 유저 id : %d 사진생성요청 Id : %d", requester.getId(), pictureGenerateRequestId)));

		if (foundPictureGenerateRequest.getPictureGenerateRequestStatus() != AWAIT_USER_VERIFICATION) {
			throw ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				foundPictureGenerateRequest.getPictureGenerateRequestStatus().getStringValue());
		}
		foundPictureGenerateRequest.userVerified();
		return true;
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
			.posePicture(PictureEntityUtils.toCommonResponse(pgreq.getPicturePose()))
			.requesterId(pgreq.getRequester().getId())
			.requestStatus(pgreq.getPictureGenerateRequestStatus())
			.cameraAngle(pgreq.getCameraAngle())
			.facePictureList(
				pgreq.getUserFacePictureList().stream().map(PictureEntityUtils::toCommonResponse).toList())
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
