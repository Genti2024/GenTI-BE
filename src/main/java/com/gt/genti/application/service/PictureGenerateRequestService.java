package com.gt.genti.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.adapter.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.application.port.in.PictureGenerateRequestPort;
import com.gt.genti.command.CreatePicturePoseCommand;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.error.DefaultErrorCode;
import com.gt.genti.error.DomainErrorCode;
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
	// private final UserRepository userRepository;
	private final OpenAIService openAIService;
	private final PictureService pictureService;
	private final RequestMatchService requestMatchService;

	public Page<PGREQDetailFindResponseDto> getAllPictureGenerateRequest(
		Pageable pageable) {

		Page<PGREQDetailFindResponseDto> result = pictureGenerateRequestPort.findAll(pageable).map(
			PGREQDetailFindResponseDto::new
		);
		if (result.isEmpty()) {
			throw new ExpectedException(DefaultErrorCode.UnHandledException, "사진생성요청이 1개도 없음");
		}
		return result;
	}

	@Override
	public List<PGREQDetailFindByUserResponseDto> getAllPictureGenerateRequestForUser(User user) {
		List<PGREQDetailFindByUserResponseDto> result = pictureGenerateRequestPort.findAllByRequester(
			user).stream().map(
			PGREQDetailFindByUserResponseDto::new
		).toList();
		if (result.isEmpty()) {
			throw new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound);
		}
		return result;
	}

	@Deprecated
	@Override
	public PGREQDetailFindByUserResponseDto findActivePGREQByUser(User user) {

		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByRequesterOrderByCreatedByDesc(
			user).orElseThrow(() -> new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));

		return new PGREQDetailFindByUserResponseDto(
			foundPictureGenerateRequest);
	}

	@Override
	public Boolean isActivePGREQExists(User user) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByRequesterOrderByCreatedByDesc(
			user).orElse(null);
		return foundPictureGenerateRequest != null;

	}

	@Override
	public PGREQDetailFindByUserResponseDto findPGREQByUserAndId(User user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findById(
				pictureGenerateRequestId)
			.orElseThrow(() ->
				new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));
		if (!Objects.equals(foundPictureGenerateRequest.getRequester().getId(), user)) {
			throw new ExpectedException(DomainErrorCode.OnlyRequesterCanViewRequest);
		}
		return new PGREQDetailFindByUserResponseDto(foundPictureGenerateRequest);
	}

	//TODO 내가 생성한 요청 리스트 보기
	// edited at 2024-04-13
	// author 서병렬
	@Override
	public List<PGREQBriefFindByUserResponseDto> getAllMyPictureGenerateRequests(User user) {
		return pictureGenerateRequestPort.findAllByRequester(user)
			.stream()
			.map(PGREQBriefFindByUserResponseDto::new).toList();
	}

	@Override
	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(User requester,
		PGREQSaveRequestDto pgreqSaveRequestDto) {

		String posePictureKey = "";
		PicturePose foundPicturePose = null;
		if (!Objects.isNull(pgreqSaveRequestDto.getPosePictureKey())) {
			posePictureKey = pgreqSaveRequestDto.getPosePictureKey();
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

		List<String> facePictureUrl = pgreqSaveRequestDto.getFacePictureKeyList();
		List<PictureUserFace> uploadedFacePictureList = pictureService.updateIfNotExistsPictureUserFace(facePictureUrl,
			requester);

		String promptAdvanced = openAIService.getAdvancedPrompt(
			new PromptAdvancementRequestCommand(pgreqSaveRequestDto.getPrompt()));
		log.info(promptAdvanced);

		PictureGenerateRequest pgr = PictureGenerateRequest.builder()
			.requester(requester)
			.promptAdvanced(promptAdvanced)
			.pgreqSaveRequestDto(pgreqSaveRequestDto)
			.picturePose(foundPicturePose)
			.userFacePictureList(uploadedFacePictureList)
			.pictureRatio(pgreqSaveRequestDto.getPictureRatio())
			.build();

		requestMatchService.matchNewRequest(pgr);
		return pictureGenerateRequestPort.save(pgr);
	}

	@Override
	public void modifyPictureGenerateRequest(User requester,
		PGREQUpdateRequestDto modifyDto) {

		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findByIdAndRequester(
				modifyDto.getPictureGenerateRequestId(), requester)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));

		if (findPictureGenerateRequest.getCreator() != null) {
			throw new ExpectedException(DomainErrorCode.RequestAlreadyInProgress);
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

}
