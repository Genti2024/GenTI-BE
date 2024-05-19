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
import com.gt.genti.command.CreatePictureProfileCommand;
import com.gt.genti.command.CreatePictureUserFaceCommand;
import com.gt.genti.domain.Picture;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PicturePost;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.error.ErrorCode;
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

	public Optional<PictureUserFace> findByUrlPictureUserFace(String url) {
		return pictureUserFaceRepository.findByUrl(url);
	}

	public List<PictureUserFace> find3ByUrlPictureUserFace(List<String> urlList) {
		return pictureUserFaceRepository.findAllByUrlIsIn(urlList);
	}

	public Optional<PictureCompleted> findByUrlPictureCompleted(String url) {
		return pictureCompletedRepository.findByUrl(url);
	}

	public Optional<PictureCreatedByCreator> findByUrlPictureCreatedByCreator(String url) {
		return pictureCreatedByCreatorRepository.findByUrl(url);
	}

	public Optional<PicturePose> findByUrlPicturePose(String url) {
		return picturePoseRepository.findByUrl(url);
	}

	public Optional<PicturePost> findByUrlPicturePost(String url) {
		return picturePostRepository.findByUrl(url);
	}

	public Optional<PictureProfile> findByUrlPictureProfile(String url) {
		return pictureProfileRepository.findByUrl(url);
	}

	public Picture updatePicture(CreatePictureCompletedCommand command) {
		User foundUser = findUser(command.getUserId());
		PictureCompleted pictureCompleted = PictureEntityUtils.makePictureCompleted(
			command.getUrl(),
			command.getPictureGenerateResponse(), foundUser);

		return pictureCompletedRepository.save(pictureCompleted);
	}

	public Picture updatePicture(CreatePictureUserFaceCommand createPictureUserFaceCommand) {
		PictureUserFace pictureUserFace = PictureEntityUtils.makePictureUserFace(
			createPictureUserFaceCommand.getUrl(),
			createPictureUserFaceCommand.getUser()
		);
		return pictureUserFaceRepository.save(pictureUserFace);
	}

	public Picture updatePicture(CreatePictureProfileCommand createPictureProfileCommand) {
		PictureProfile pictureProfile = PictureEntityUtils.makePictureProfile(
			createPictureProfileCommand.getUrl(),
			createPictureProfileCommand.getUser()
		);
		return pictureProfileRepository.save(pictureProfile);
	}

	public PicturePose updatePicture(CreatePicturePoseCommand command) {

		PicturePose picturePose = PictureEntityUtils.makePicturePose(
			command.getUrl(),
			findUser(command.getUserId())
		);
		return picturePoseRepository.save(picturePose);
	}

	public Picture updatePicture(CreatePictureCreatedByCreatorCommand command) {

		PictureCreatedByCreator pictureCreatedByCreator = PictureEntityUtils.makePictureCreatedByCreator(
			command.getUrl(),
			command.getPictureGenerateResponse(),
			findUser(command.getUserId())
		);
		return pictureCreatedByCreatorRepository.save(pictureCreatedByCreator);
	}

	public List<PictureUserFace> updatePictureUserFaceAll(
		List<CreatePictureUserFaceCommand> createPictureUserFaceCommand) {
		List<PictureUserFace> pictureUserFaceList = createPictureUserFaceCommand.stream()
			.map(d -> PictureEntityUtils.makePictureUserFace(
				d.getUrl(),
				d.getUser()
			))
			.toList();
		return pictureUserFaceRepository.saveAll(pictureUserFaceList);
	}

	public List<PictureUserFace> updateIfNotExistsPictureUserFace(List<String> facePictureUrl, User foundRequester) {
		Set<PictureUserFace> foundFacePictureSet = new HashSet<>(
			this.find3ByUrlPictureUserFace(facePictureUrl));

		if (foundFacePictureSet.size() < 3) {
			Set<String> foundFacePictureUrlSet = foundFacePictureSet.stream()
				.map(PictureUserFace::getUrl)
				.collect(Collectors.toSet());

			List<CreatePictureUserFaceCommand> notExistFacePictureList = facePictureUrl.stream()
				.filter(givenUrl -> !foundFacePictureUrlSet.contains(givenUrl))
				.map(url -> CreatePictureUserFaceCommand.builder()
					.url(url)
					.user(foundRequester)
					.build())
				.toList();

			List<PictureUserFace> savedPictureUserFaceList = this.updatePictureUserFaceAll(
				notExistFacePictureList);
			foundFacePictureSet.addAll(savedPictureUserFaceList);
		}
		return foundFacePictureSet.stream().toList();
	}

	public List<PictureCreatedByCreator> updateAll(List<CreatePictureCreatedByCreatorCommand> newUploadPictures) {
		User foundUser = findUser(newUploadPictures.get(0).getUserId());
		List<PictureCreatedByCreator> uploadEntityList = newUploadPictures.stream()
			.map(command -> PictureEntityUtils.makePictureCreatedByCreator(
				command.getUrl(),
				command.getPictureGenerateResponse(),
				foundUser
			))
			.toList();
		return pictureCreatedByCreatorRepository.saveAll(uploadEntityList);
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));
	}
}
