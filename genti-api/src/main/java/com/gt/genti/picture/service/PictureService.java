package com.gt.genti.picture.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.common.picture.Picture;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picture.completed.repository.PictureCompletedRepository;
import com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator;
import com.gt.genti.picture.createdbycreator.repository.PictureCreatedByCreatorRepository;
import com.gt.genti.picture.pose.model.PicturePose;
import com.gt.genti.picture.pose.repository.PicturePoseRepository;
import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.picture.profile.repository.PictureProfileRepository;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.picture.userface.repository.PictureUserFaceRepository;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;
import com.gt.genti.util.PictureEntityUtils;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.command.CreatePictureCompletedCommand;
import com.gt.genti.picture.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.picture.command.CreatePicturePoseCommand;
import com.gt.genti.picture.command.CreatePictureUserFaceCommand;
import com.gt.genti.picture.command.PictureProfileSaveCommand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PictureService {
	private final PictureCreatedByCreatorRepository pictureCreatedByCreatorRepository;
	private final PictureUserFaceRepository pictureUserFaceRepository;
	private final PictureProfileRepository pictureProfileRepository;
	private final PictureCompletedRepository pictureCompletedRepository;
	private final PicturePoseRepository picturePoseRepository;

	public List<PictureCompleted> findAllPictureCompletedByPictureGenerateResponse(PictureGenerateResponse pgres) {
		return pictureCompletedRepository.findAllByPictureGenerateResponse(pgres);
	}

	public PictureUserFace findByUrlPictureUserFace(String key) {
		return pictureUserFaceRepository.findByKey(key)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureUserFaceNotFound));
	}

	// public PictureCompleted findByUrlPictureCompleted(String key) {
	// 	return pictureCompletedRepository.findByUrl(key);
	// }

	public PictureCreatedByCreator findByUrlPictureCreatedByCreator(String key) {
		return pictureCreatedByCreatorRepository.findByKey(key)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureCreatedByCreatorNotFound));
	}

	public Optional<PicturePose> findByKeyPicturePose(String key) {
		return picturePoseRepository.findByKey(key);
	}

	public PictureProfile findByKeyPictureProfile(String key) {
		return pictureProfileRepository.findByKey(key)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureProfileNotFound));
	}

	public PictureCreatedByCreator findPictureCreatedByCreatorByPictureGenerateResponse(
		PictureGenerateResponse pictureGenerateResponse) {
		return pictureCreatedByCreatorRepository.findByPictureGenerateResponse(pictureGenerateResponse)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureCreatedByCreatorNotFound));
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
				command.getPictureGenerateResponse(),
				command.getUploader(),
				command.getRequester(),
				command.getPictureRatio())
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

	public List<PictureProfile> findAllProfilePicture(User foundUser) {
		return pictureProfileRepository.findAllByUserOrderByCreatedAtDesc(foundUser);
	}

	public List<PictureUserFace> find3PictureUserFaceByKeyList(List<String> keyList) {
		return pictureUserFaceRepository.findAllByKeyIsIn(keyList);
	}

}
