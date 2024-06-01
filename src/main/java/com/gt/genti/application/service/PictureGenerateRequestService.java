package com.gt.genti.application.service;

import java.util.List;

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
import com.gt.genti.dto.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.PGREQSaveRequestDto;
import com.gt.genti.dto.PGREQUpdateRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.external.openai.dto.PromptAdvancementRequestDto;
import com.gt.genti.external.openai.service.OpenAIService;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.UserRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureGenerateRequestService implements PictureGenerateRequestUseCase {
	private final PictureGenerateRequestPort pictureGenerateRequestPort;
	private final CreatorRepository creatorRepository;
	private final UserRepository userRepository;
	private final OpenAIService openAIService;
	private final PictureService pictureService;
	private final RequestMatchService requestMatchService;

	public Page<PGREQDetailFindResponseDto> getAllPictureGenerateRequest(
		Pageable pageable) {

		Page<PGREQDetailFindResponseDto> result = pictureGenerateRequestPort.findAll(pageable).map(
			PGREQDetailFindResponseDto::new
		);
		if (result.isEmpty()) {
			throw new ExpectedException(ErrorCode.Undefined);
		}
		return result;
	}

	@Override
	public List<PGREQDetailFindByUserResponseDto> getAllPictureGenerateRequestForUser(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));
		List<PGREQDetailFindByUserResponseDto> result = pictureGenerateRequestPort.findAllByRequester(
			foundUser).stream().map(
			PGREQDetailFindByUserResponseDto::new
		).toList();
		if (result.isEmpty()) {
			throw new ExpectedException(ErrorCode.ActivePictureGenerateRequestNotExists);
		}
		return result;
	}

	@Override
	public PGREQDetailFindByUserResponseDto getPictureGenerateRequestForUser(Long userId) {

		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByUserIdOrderByCreatedByDesc(
			userId).orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));

		return new PGREQDetailFindByUserResponseDto(
			foundPictureGenerateRequest);
	}

	@Override
	public PGREQDetailFindResponseDto getPictureGenerateRequestById(Long pictureGenerateRequestId) {
		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findById(
				pictureGenerateRequestId)
			.orElseThrow(() ->
				new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));
		return new PGREQDetailFindResponseDto(findPictureGenerateRequest);
	}

	//TODO 내가 생성한 요청 리스트 보기
	// edited at 2024-04-13
	// author 서병렬
	@Override
	public List<PGREQBriefFindByUserResponseDto> getAllMyPictureGenerateRequests(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));
		return pictureGenerateRequestPort.findAllByRequester(foundUser)
			.stream()
			.map(PGREQBriefFindByUserResponseDto::new).toList();
	}

	@Override
	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(Long requesterId,
		PGREQSaveRequestDto PGREQSaveRequestDto) {

		User foundRequester = userRepository.findById(requesterId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));

		String posePictureUrl = PGREQSaveRequestDto.getPosePictureUrl();

		PicturePose foundPicturePose = pictureService.findByUrlPicturePose(posePictureUrl)
			.orElseGet(() -> pictureService.updatePicture(
				CreatePicturePoseCommand.builder().url(posePictureUrl).user(foundRequester).build()));

		List<String> facePictureUrl = PGREQSaveRequestDto.getFacePictureUrlList();

		String promptAdvanced = openAIService.getAdvancedPrompt(
			new PromptAdvancementRequestDto(PGREQSaveRequestDto.getPrompt()));
		log.info(promptAdvanced);

		List<PictureUserFace> uploadedFacePictureList = pictureService.updateIfNotExistsPictureUserFace(facePictureUrl,
			foundRequester);

		PictureGenerateRequest pgr = PictureGenerateRequest.builder()
			.requester(foundRequester)
			.promptAdvanced(promptAdvanced)
			.pgreqSaveRequestDto(PGREQSaveRequestDto)
			.picturePose(foundPicturePose)
			.userFacePictureList(uploadedFacePictureList)
			.build();

		requestMatchService.matchNewRequest(pgr);
		return pictureGenerateRequestPort.save(pgr);
	}

	@Override
	public void modifyPictureGenerateRequest(Long userId,
		PGREQUpdateRequestDto modifyDto) {

		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findByIdAndRequesterId(
				modifyDto.getPictureGenerateRequestId(), userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));

		if (findPictureGenerateRequest.getCreator() != null) {
			throw new ExpectedException(ErrorCode.RequestAlreadyInProgress);
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
