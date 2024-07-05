package com.gt.genti.domain;

import static com.gt.genti.error.ResponseCode.*;
import static com.gt.genti.other.util.DateTimeUtils.*;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.domain.enums.converter.db.CameraAngleConverter;
import com.gt.genti.domain.enums.converter.db.PictureRatioConverter;
import com.gt.genti.domain.enums.converter.db.RequestStatusConverter;
import com.gt.genti.domain.enums.converter.db.ShotCoverageConverter;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.error.ExpectedException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Table(name = "picture_generate_request")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureGenerateRequest extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "requester_id", nullable = false)
	User requester;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	Creator creator;

	@OneToMany(mappedBy = "request")
	List<PictureGenerateResponse> responseList;

	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(name = "picture_generate_request_picture_user_face")
	List<PictureUserFace> userFacePictureList;

	@Column(name = "prompt", nullable = false, length = 511)
	String prompt;

	@Column(name = "prompt_advanced", length = 4095)
	String promptAdvanced;

	@ManyToOne
	@JoinColumn(name = "picture_pose_id")
	PicturePose picturePose;

	@Column(name = "camera_angle", nullable = false)
	@Convert(converter = CameraAngleConverter.class)
	CameraAngle cameraAngle;

	@Column(name = "shot_coverage", nullable = false)
	@Convert(converter = ShotCoverageConverter.class)
	ShotCoverage shotCoverage;

	@Column(name = "request_status", nullable = false)
	@Convert(converter = RequestStatusConverter.class)
	PictureGenerateRequestStatus pictureGenerateRequestStatus;

	@Column(name = "picture_ratio", nullable = false)
	@Convert(converter = PictureRatioConverter.class)
	PictureRatio pictureRatio;

	@Column(name = "match_to_admin", nullable = false)
	Boolean matchToAdmin;

	@Builder
	public PictureGenerateRequest(User requester, PGREQSaveCommand pgreqSaveCommand,
		PicturePose picturePose, List<PictureUserFace> userFacePictureList, String promptAdvanced) {
		this.requester = requester;
		this.prompt = pgreqSaveCommand.getPrompt();
		this.promptAdvanced = promptAdvanced;
		this.picturePose = picturePose;
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.CREATED;
		this.cameraAngle = pgreqSaveCommand.getCameraAngle();
		this.shotCoverage = pgreqSaveCommand.getShotCoverage();
		this.userFacePictureList = userFacePictureList;
		this.pictureRatio = pgreqSaveCommand.getPictureRatio();
	}

	public void modify(PGREQSaveRequestDto pgreqSaveRequestDto, PicturePose picturePose,
		List<PictureUserFace> pictureUserFaceList) {
		this.prompt = pgreqSaveRequestDto.getPrompt();
		this.cameraAngle = pgreqSaveRequestDto.getCameraAngle();
		this.shotCoverage = pgreqSaveRequestDto.getShotCoverage();
		this.picturePose = picturePose;
		this.userFacePictureList = pictureUserFaceList;
	}

	public void assignToCreator(Creator creator) {
		if (this.pictureGenerateRequestStatus != PictureGenerateRequestStatus.CREATED) {
			log.error(" 이미 진행중인 작업에 대해 비 정상적인 매칭");
			return;
		}
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.ASSIGNING;
		this.creator = creator;
		this.matchToAdmin = false;
	}

	public void acceptByCreator() {
		if (LocalDateTime.now().isAfter(this.getModifiedAt().plusMinutes(ACCEPTABLE_TIME_MINUTE))) {
			throw ExpectedException.withLogging(SubmitBlockedDueToPictureGenerateResponseIsExpired);
		}
		this.requester.addRequestCount();
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.IN_PROGRESS;
	}

	public void rejectByCreator() {
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.CREATED;

	}

	public void assignToAdmin(Creator creator) {
		this.requester.addRequestCount();
		this.creator = creator;
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.MATCH_TO_ADMIN;
		this.matchToAdmin = false;
	}

	public void submittedByAdmin() {
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.AWAIT_USER_VERIFICATION;
	}

	public void userVerified() {
		this.pictureGenerateRequestStatus = PictureGenerateRequestStatus.COMPLETED;
	}
}
