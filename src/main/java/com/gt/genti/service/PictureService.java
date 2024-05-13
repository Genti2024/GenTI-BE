package com.gt.genti.service;

import java.util.List;
import java.util.Optional;

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
import com.gt.genti.other.util.PictureEntityUtils;
import com.gt.genti.repository.PictureCompletedRepository;
import com.gt.genti.repository.PictureCreatedByCreatorRepository;
import com.gt.genti.repository.PicturePoseRepository;
import com.gt.genti.repository.PicturePostRepository;
import com.gt.genti.repository.PictureProfileRepository;
import com.gt.genti.repository.PictureUserFaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureService {
	private final PictureCreatedByCreatorRepository pictureCreatedByCreatorRepository;
	private final PictureUserFaceRepository pictureUserFaceRepository;
	private final PicturePostRepository picturePostRepository;
	private final PictureProfileRepository pictureProfileRepository;
	private final PictureCompletedRepository pictureCompletedRepository;
	private final PicturePoseRepository picturePoseRepository;

	public Optional<PictureUserFace> findByUrlPictureUserFace(String url) {
		return pictureUserFaceRepository.findByUrl(url);
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

	public Picture uploadPicture(CreatePictureCompletedCommand createPictureCompletedCommand) {
		PictureCompleted pictureCompleted = PictureEntityUtils.makePictureCompleted(
			createPictureCompletedCommand.getUrl(),
			createPictureCompletedCommand.getPictureGenerateResponse(), createPictureCompletedCommand.getUploadedBy());
		return pictureCompletedRepository.save(pictureCompleted);
	}

	public Picture uploadPicture(CreatePictureUserFaceCommand createPictureUserFaceCommand) {
		PictureUserFace pictureUserFace = PictureEntityUtils.makePictureUserFace(
			createPictureUserFaceCommand.getUrl(),
			createPictureUserFaceCommand.getUser()
		);
		return pictureUserFaceRepository.save(pictureUserFace);
	}

	public Picture uploadPicture(CreatePictureProfileCommand createPictureProfileCommand) {
		PictureProfile pictureProfile = PictureEntityUtils.makePictureProfile(
			createPictureProfileCommand.getUrl(),
			createPictureProfileCommand.getUser()
		);
		return pictureProfileRepository.save(pictureProfile);
	}

	public PicturePose uploadPicture(CreatePicturePoseCommand createPicturePoseCommand) {
		PicturePose picturePose = PictureEntityUtils.makePicturePose(
			createPicturePoseCommand.getUrl(),
			createPicturePoseCommand.getUploadedBy()
		);
		return picturePoseRepository.save(picturePose);
	}

	public Picture uploadPicture(CreatePictureCreatedByCreatorCommand createPictureCreatedByCreatorCommand) {
		PictureCreatedByCreator pictureCreatedByCreator = PictureEntityUtils.makePictureCreatedByCreator(
			createPictureCreatedByCreatorCommand.getUrl(),
			createPictureCreatedByCreatorCommand.getPictureGenerateResponse(),
			createPictureCreatedByCreatorCommand.getUploadedBy()
		);
		return pictureCreatedByCreatorRepository.save(pictureCreatedByCreator);
	}

	public List<PictureUserFace> uploadPictures(List<CreatePictureUserFaceCommand> createPictureUserFaceCommand) {
		List<PictureUserFace> pictureUserFaceList = createPictureUserFaceCommand.stream()
			.map(d -> PictureEntityUtils.makePictureUserFace(
				d.getUrl(),
				d.getUser()
			))
			.toList();
		return pictureUserFaceRepository.saveAll(pictureUserFaceList);
	}

}
