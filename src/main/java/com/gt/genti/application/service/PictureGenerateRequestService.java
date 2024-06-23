package com.gt.genti.application.service;

import static com.gt.genti.domain.enums.converter.db.EnumUtil.*;
import static com.gt.genti.error.ResponseCode.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gt.genti.adapter.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.application.port.in.PictureGenerateRequestPort;
import com.gt.genti.command.CreatePicturePoseCommand;
import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.admin.response.PGREQDetailFindResponseDto;
import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.external.openai.dto.PromptAdvancementRequestCommand;
import com.gt.genti.external.openai.service.OpenAIService;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureGenerateRequestService implements PictureGenerateRequestUseCase {
	private final PictureGenerateRequestPort pictureGenerateRequestPort;
	private final CreatorRepository creatorRepository;
	private final OpenAIService openAIService;
	private final PictureService pictureService;
	private final RequestMatchService requestMatchService;

	public Page<PGREQDetailFindResponseDto> getAllPictureGenerateRequest(
		Pageable pageable) {

		Page<PGREQDetailFindResponseDto> result = pictureGenerateRequestPort.findAll(pageable).map(
			PGREQDetailFindResponseDto::new
		);
		if (result.isEmpty()) {
			throw ExpectedException.withLogging(NoPictureGenerateRequest);
		}
		return result;
	}

	@Override
	public List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(User user) {
		List<PGREQBriefFindByUserResponseDto> result = pictureGenerateRequestPort.findAllByRequester(
			user).stream().map(
			PGREQBriefFindByUserResponseDto::new
		).sorted((dto1, dto2) -> dto2.getCreatedAt().compareTo(dto1.getCreatedAt())).toList();
		if (result.isEmpty()) {
			throw ExpectedException.withLogging(PictureGenerateRequestNotFound);
		}
		return result;
	}

	@Override
	public Boolean isPendingPGREQExists(User user) {
		return pictureGenerateRequestPort.findByRequesterAndStatusInOrderByCreatedByDesc(
			user, PGREQ_STATUS_PENDING).isPresent();
	}

	@Override
	public PGREQDetailFindByUserResponseDto findPGREQByRequestAndId(User user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findById(
				pictureGenerateRequestId)
			.orElseThrow(() ->
				ExpectedException.withLogging(PictureGenerateRequestNotFound));
		if (!Objects.equals(foundPictureGenerateRequest.getRequester().getId(), user.getId())) {
			throw ExpectedException.withLogging(OnlyRequesterCanViewRequest);
		}
		return new PGREQDetailFindByUserResponseDto(foundPictureGenerateRequest);
	}

	@Override
	public PictureGenerateRequest createPGREQ(User requester,
		PGREQSaveCommand pgreqSaveCommand) {

		String posePictureKey = "";
		PicturePose foundPicturePose = null;
		if (!Objects.isNull(pgreqSaveCommand.getPosePictureKey())) {
			posePictureKey = pgreqSaveCommand.getPosePictureKey();
			String finalPosePictureKey = posePictureKey;
			foundPicturePose = pictureService.findByUrlPicturePose(posePictureKey)
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

		PictureGenerateRequest savedPGREQ = pictureGenerateRequestPort.save(pgr);
		requestMatchService.matchNewRequest(savedPGREQ);

		return savedPGREQ;
	}

	@Override
	public void modifyPGREQ(User requester,
		PGREQUpdateRequestDto modifyDto) {

		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findByIdAndRequester(
				modifyDto.getPictureGenerateRequestId(), requester)
			.orElseThrow(() -> ExpectedException.withLogging(PictureGenerateRequestNotFound));

		if (findPictureGenerateRequest.getCreator() != null) {
			throw ExpectedException.withLogging(RequestAlreadyInProgress);
		}

		PicturePose picturePose = findPictureGenerateRequest.getPicturePose();
		String givenPicturePoseUrl = modifyDto.getPosePictureUrl();
		picturePose.modify(givenPicturePoseUrl);

		List<PictureUserFace> pictureUserFaceList = findPictureGenerateRequest.getUserFacePictureList();
		List<String> givenPictureUserFaceUrlList = modifyDto.getFacePictureUrlList();
		for (int i = 0; i < pictureUserFaceList.size(); i++) {
			String newUrl = givenPictureUserFaceUrlList.get(i);
			pictureUserFaceList.get(i).modify(newUrl);
		}

		findPictureGenerateRequest.modify(modifyDto, picturePose, pictureUserFaceList);
	}

	@Override
	public PGREQBriefFindByUserResponseDto findByRequestAndStatusIs(User requester,
		PictureGenerateRequestStatus status) {
		return pictureGenerateRequestPort.findByRequesterAndStatusInOrderByCreatedByDesc(requester, List.of(status))
			.map(PGREQBriefFindByUserResponseDto::new)
			.orElseThrow(() -> ExpectedException.withLogging(NoPendingUserVerificationPGREQ));
	}

}
