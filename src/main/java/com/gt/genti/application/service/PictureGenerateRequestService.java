package com.gt.genti.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gt.genti.adapter.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.application.port.in.PictureGenerateRequestPort;
import com.gt.genti.application.port.in.PictureUserFacePort;
import com.gt.genti.command.CreatePicturePoseCommand;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.dto.PictureGenerateRequestSimplifiedResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.external.openai.dto.PromptAdvancementRequestDto;
import com.gt.genti.external.openai.service.OpenAIService;
import com.gt.genti.other.util.RandomUtils;
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
	private final PictureUserFacePort pictureUserFacePort;
	private final OpenAIService openAIService;
	private final PictureService pictureService;

	@Override
	public List<PictureGenerateRequestDetailResponseDto> getPictureGenerateRequest(Long userId,
		PictureGenerateRequestStatus status) {
		List<PictureGenerateRequestDetailResponseDto> result = pictureGenerateRequestPort.findByRequestStatusAndUserId(
			status, userId).stream().map(
			PictureGenerateRequestDetailResponseDto::new
		).toList();
		if (result.isEmpty()) {
			throw new ExpectedException(ErrorCode.ActivePictureGenerateRequestNotExists);
		}
		return result;
	}

	@Override
	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequest(Long userId) {

		PictureGenerateRequest foundPictureGenerateRequest = pictureGenerateRequestPort.findByUserIdOrderByCreatedByDesc(
			userId).orElseThrow(() -> new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));

		PictureGenerateRequestDetailResponseDto result = new PictureGenerateRequestDetailResponseDto(
			foundPictureGenerateRequest);

		return result;
	}

	@Override
	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long pictureGenerateRequestId) {
		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findById(
				pictureGenerateRequestId)
			.orElseThrow(() ->
				new ExpectedException(ErrorCode.PictureGenerateRequestNotFound));
		return new PictureGenerateRequestDetailResponseDto(findPictureGenerateRequest);
	}

	//TODO 내가 생성한 요청 리스트 보기
	// edited at 2024-04-13
	// author 서병렬
	@Override
	public List<PictureGenerateRequestSimplifiedResponseDto> getAllMyPictureGenerateRequests(User requester) {
		// return pictureGenerateRequestPersistenceAdapter.findAllByRequester(requester).stream().map(entity -> );
		return null;
	}

	@Override
	public Boolean createPictureGenerateRequest(Long requesterId,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto) {

		User foundRequester = userRepository.findById(requesterId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));

		String posePictureUrl = pictureGenerateRequestRequestDto.getPosePictureUrl();
		Optional<PicturePose> optionalPicturePose = pictureService.findByUrlPicturePose(posePictureUrl);
		PicturePose foundPicturePose;
		if (optionalPicturePose.isEmpty()) {
			foundPicturePose = pictureService.uploadPicture(
				CreatePicturePoseCommand.builder().url(posePictureUrl).uploadedBy(requesterId).build());
		} else {
			foundPicturePose = optionalPicturePose.get();
		}

		List<String> facePictureUrl = pictureGenerateRequestRequestDto.getFacePictureUrlList();

		String promptAdvanced = openAIService.getAdvancedPrompt(
			new PromptAdvancementRequestDto(pictureGenerateRequestRequestDto.getPrompt()));
		log.info(promptAdvanced);

		Set<PictureUserFace> foundFacePictureSet = new HashSet<>(
			pictureUserFacePort.findPictureByUrlIn(facePictureUrl));

		if (foundFacePictureSet.size() < 3) {
			Set<String> foundFacePictureUrlSet = foundFacePictureSet.stream()
				.map(PictureUserFace::getUrl)
				.collect(Collectors.toSet());

			List<PictureUserFace> notExistFacePictureList = facePictureUrl.stream()
				.filter(givenUrl -> !foundFacePictureUrlSet.contains(givenUrl))
				.map(url -> PictureUserFace.builder()
					.url(url)
					.user(foundRequester)
					.build())
				.toList();

			List<PictureUserFace> savedPictureUserFaceList = pictureUserFacePort.saveAll(notExistFacePictureList);
			foundFacePictureSet.addAll(savedPictureUserFaceList);
		}

		PictureGenerateRequest pgr = PictureGenerateRequest.builder()
			.requester(foundRequester)
			.promptAdvanced(promptAdvanced)
			.pictureGenerateRequestRequestDto(pictureGenerateRequestRequestDto)
			.picturePose(foundPicturePose)
			.userFacePictureList(foundFacePictureSet.stream().toList())
			.build();

		matchCreatorIfAvailable(pgr);
		pictureGenerateRequestPort.save(pgr);

		return true;
	}

	@Override
	public Boolean modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto modifyDto) {

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
		return true;
	}

	private void matchCreatorIfAvailable(PictureGenerateRequest pgr) {
		List<Creator> creatorList = creatorRepository.findAllAvailableCreator();
		if (!creatorList.isEmpty()) {
			Creator randomSelectedCreator = RandomUtils.getRandomElement(creatorList);
			pgr.assign(randomSelectedCreator);
			sendNotification(randomSelectedCreator);
		}
	}

	private void sendNotification(Creator creator) {
		//TODO 공급자 앱에 푸시알림
		// edited at 2024-05-04
		// author
	}

}
