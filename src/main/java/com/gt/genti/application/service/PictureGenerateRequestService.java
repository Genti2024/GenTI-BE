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
import com.gt.genti.dto.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.PGREQSaveRequestDto;
import com.gt.genti.dto.PGREQUpdateRequestDto;
import com.gt.genti.error.DefaultErrorCode;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.external.openai.dto.PromptAdvancementRequestCommand;
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
			throw new ExpectedException(DefaultErrorCode.UnHandledException, "사진생성요청이 1개도 없음");
		}
		return result;
	}

	@Override
	public List<PGREQDetailFindByUserResponseDto> getAllPictureGenerateRequestForUser(Long userId) {
		User foundUser = findUser(userId);
		
		List<PGREQDetailFindByUserResponseDto> result = pictureGenerateRequestPort.findAllByRequester(
			foundUser).stream().map(
			PGREQDetailFindByUserResponseDto::new
		).toList();
		if (result.isEmpty()) {
			throw new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound);
		}
		return result;
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound));
	}

	@Override
	public PGREQDetailFindByUserResponseDto findActivePGREQByUser(Long userId) {

		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByUserIdOrderByCreatedByDesc(
			userId).orElseThrow(() -> new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));

		return new PGREQDetailFindByUserResponseDto(
			foundPictureGenerateRequest);
	}

	@Override
	public Boolean isActivePGREQExists(Long userId) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByUserIdOrderByCreatedByDesc(
			userId).orElse(null);
		return foundPictureGenerateRequest != null;

	}

	@Override
	public PGREQDetailFindByUserResponseDto findPGREQByUserAndId(Long userId, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findById(
				pictureGenerateRequestId)
			.orElseThrow(() ->
				new ExpectedException(DomainErrorCode.PictureGenerateRequestNotFound));
		if (!Objects.equals(foundPictureGenerateRequest.getRequester().getId(), userId)) {
			throw new ExpectedException(DomainErrorCode.OnlyRequesterCanViewRequest);
		}
		return new PGREQDetailFindByUserResponseDto(foundPictureGenerateRequest);
	}

	//TODO 내가 생성한 요청 리스트 보기
	// edited at 2024-04-13
	// author 서병렬
	@Override
	public List<PGREQBriefFindByUserResponseDto> getAllMyPictureGenerateRequests(Long userId) {
		User foundUser = findUser(userId);
		return pictureGenerateRequestPort.findAllByRequester(foundUser)
			.stream()
			.map(PGREQBriefFindByUserResponseDto::new).toList();
	}

	@Override
	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(Long requesterId,
		PGREQSaveRequestDto PGREQSaveRequestDto) {

		User foundUploader = findUser(requesterId);

		String posePictureKey = PGREQSaveRequestDto.getPosePictureKey();

		PicturePose foundPicturePose = pictureService.findByUrlPicturePose(posePictureKey)
			.orElseGet(() -> {
				log.info("""
					%s 유저가 요청에 포함한 포즈참고사진 key [%s] 기존 사진을 찾을 수 없어 신규 저장"""
					.formatted(foundUploader.getEmail(), posePictureKey));
				return pictureService.updatePicture(
					CreatePicturePoseCommand.builder()
						.key(posePictureKey)
						.uploader(foundUploader).build());
			});

		List<String> facePictureUrl = PGREQSaveRequestDto.getFacePictureKeyList();
		List<PictureUserFace> uploadedFacePictureList = pictureService.updateIfNotExistsPictureUserFace(facePictureUrl,
			foundUploader);

		String promptAdvanced = openAIService.getAdvancedPrompt(
			new PromptAdvancementRequestCommand(PGREQSaveRequestDto.getPrompt()));
		log.info(promptAdvanced);

		PictureGenerateRequest pgr = PictureGenerateRequest.builder()
			.requester(foundUploader)
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
