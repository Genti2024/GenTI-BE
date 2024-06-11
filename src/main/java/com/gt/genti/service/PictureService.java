package com.gt.genti.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gt.genti.command.CreatePictureCompletedCommand;
import com.gt.genti.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.command.CreatePicturePoseCommand;
import com.gt.genti.command.CreatePictureUserFaceCommand;
import com.gt.genti.command.PictureProfileSaveCommand;
import com.gt.genti.domain.common.Picture;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.util.PictureEntityUtils;
import com.gt.genti.repository.PictureCompletedRepository;
import com.gt.genti.repository.PictureCreatedByCreatorRepository;
import com.gt.genti.repository.PicturePoseRepository;
import com.gt.genti.repository.PicturePostRepository;
import com.gt.genti.repository.PictureProfileRepository;
import com.gt.genti.repository.PictureUserFaceRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureService {
	private final UserRepository userRepository;
	private final PictureCreatedByCreatorRepository pictureCreatedByCreatorRepository;
	private final PictureUserFaceRepository pictureUserFaceRepository;
	private final PicturePostRepository picturePostRepository;
	private final PictureProfileRepository pictureProfileRepository;
	private final PictureCompletedRepository pictureCompletedRepository;
	private final PicturePoseRepository picturePoseRepository;

	public List<PictureCompleted> findAllPictureCompletedByPictureGenerateResponse(PictureGenerateResponse pgres) {
		return pictureCompletedRepository.findAllByPictureGenerateResponse(pgres);
	}

	public PictureUserFace findByUrlPictureUserFace(String key) {
		return pictureUserFaceRepository.findByKey(key)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureUserFaceNotFound));
	}

	// public PictureCompleted findByUrlPictureCompleted(String key) {
	// 	return pictureCompletedRepository.findByUrl(key);
	// }

	public PictureCreatedByCreator findByUrlPictureCreatedByCreator(String key) {
		return pictureCreatedByCreatorRepository.findByKey(key)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureCreatedByCreatorNotFound));
	}

	public Optional<PicturePose> findByUrlPicturePose(String key) {
		return picturePoseRepository.findByKey(key);
	}

	public PictureProfile findByUrlPictureProfile(String key) {
		return pictureProfileRepository.findByKey(key)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureProfileNotFound));
	}

	public PictureCreatedByCreator findPictureCreatedByCreatorByPictureGenerateResponse(
		PictureGenerateResponse pictureGenerateResponse) {
		return pictureCreatedByCreatorRepository.findByPictureGenerateResponse(pictureGenerateResponse)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureCreatedByCreatorNotFound));
	}

	public Picture updatePicture(CreatePictureCompletedCommand command) {
		PictureCompleted pictureCompleted = PictureCompleted.builder()
			.uploadedBy(command.getUploader())
			.key(command.getKey())
			.pictureGenerateResponse(command.getPictureGenerateResponse())
			.build();

		return pictureCompletedRepository.save(pictureCompleted);
	}

	public List<PictureCompleted> updatePictures(List<CreatePictureCompletedCommand> commandList) {

		List<PictureCompleted> pictureCompletedList = commandList.stream().map(
			command -> PictureEntityUtils.makePictureCompleted(
				command.getKey(),
				command.getPictureGenerateResponse(), command.getUploader())
		).toList();

		return pictureCompletedRepository.saveAll(pictureCompletedList);
	}

	public PictureUserFace updatePicture(CreatePictureUserFaceCommand createPictureUserFaceCommand) {
		PictureUserFace pictureUserFace = PictureEntityUtils.makePictureUserFace(
			createPictureUserFaceCommand.getKey(),
			createPictureUserFaceCommand.getUploader()
		);
		return pictureUserFaceRepository.save(pictureUserFace);
	}

	public Picture updatePicture(PictureProfileSaveCommand pictureProfileSaveCommand) {
		PictureProfile pictureProfile = PictureEntityUtils.makePictureProfile(
			pictureProfileSaveCommand.getKey(),
			pictureProfileSaveCommand.getUploader()
		);
		return pictureProfileRepository.save(pictureProfile);
	}

	public PicturePose updatePicture(CreatePicturePoseCommand command) {

		PicturePose picturePose = PictureEntityUtils.makePicturePose(
			command.getKey(),
			command.getUploader()
		);
		return picturePoseRepository.save(picturePose);
	}

	public Picture updatePicture(CreatePictureCreatedByCreatorCommand command) {

		PictureCreatedByCreator pictureCreatedByCreator = PictureEntityUtils.makePictureCreatedByCreator(
			command.getKey(),
			command.getPictureGenerateResponse(),
			command.getUploader()
		);
		return pictureCreatedByCreatorRepository.save(pictureCreatedByCreator);
	}

	public List<PictureUserFace> updatePictureUserFaceAll(
		List<CreatePictureUserFaceCommand> createPictureUserFaceCommand) {
		List<PictureUserFace> pictureUserFaceList = createPictureUserFaceCommand.stream()
			.map(command -> PictureEntityUtils.makePictureUserFace(
				command.getKey(),
				command.getUploader()
			))
			.toList();
		return pictureUserFaceRepository.saveAll(pictureUserFaceList);
	}

	public List<PictureUserFace> updateIfNotExistsPictureUserFace(List<String> facePictureKeyList, User uploader) {
		Set<PictureUserFace> foundFacePictureSet = new HashSet<>(
			this.find3PictureUserFaceByKeyList(facePictureKeyList));

		if (foundFacePictureSet.size() < 3) {
			Set<String> foundFacePictureUrlSet = foundFacePictureSet.stream()
				.map(PictureUserFace::getKey)
				.collect(Collectors.toSet());

			List<CreatePictureUserFaceCommand> notExistFacePictureList = facePictureKeyList.stream()
				.filter(givenUrl -> !foundFacePictureUrlSet.contains(givenUrl))
				.map(key -> {
					log.info("""
						%s 유저가 요청에 포함한 얼굴사진  key [%s] 기존 사진을 찾을 수 없어 신규 저장"""
						.formatted(uploader.getEmail(), key));
					return (CreatePictureUserFaceCommand)CreatePictureUserFaceCommand.builder()
						.key(key)
						.uploader(uploader)
						.build();
				})
				.toList();

			List<PictureUserFace> savedPictureUserFaceList = this.updatePictureUserFaceAll(
				notExistFacePictureList);
			foundFacePictureSet.addAll(savedPictureUserFaceList);
		}
		return foundFacePictureSet.stream().toList();
	}

	public List<PictureCreatedByCreator> updateAll(List<CreatePictureCreatedByCreatorCommand> newUploadPictures) {
		List<PictureCreatedByCreator> uploadEntityList = newUploadPictures.stream()
			.map(command -> PictureCreatedByCreator.builder()
				.key(command.getKey())
				.pictureGenerateResponse(command.getPictureGenerateResponse())
				.uploadedBy(command.getUploader())
				.build())
			.toList();
		return pictureCreatedByCreatorRepository.saveAll(uploadEntityList);
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.UserNotFound));
	}

	public List<PictureProfile> findAllProfilePicture(User foundUser) {
		return pictureProfileRepository.findAllByUserOrderByCreatedAtDesc(foundUser);
	}

	public List<PictureUserFace> find3PictureUserFaceByKeyList(List<String> keyList) {
		return pictureUserFaceRepository.findAllByKeyIsIn(keyList);
	}

}
