package com.gt.genti.picturegeneraterequest.model;

import static com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.common.converter.CameraAngleConverter;
import com.gt.genti.common.converter.PictureRatioConverter;
import com.gt.genti.common.converter.RequestStatusConverter;
import com.gt.genti.common.converter.ShotCoverageConverter;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.pose.model.PicturePose;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;
import com.gt.genti.util.DateTimeUtil;

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
import jakarta.persistence.PrePersist;
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
	List<PictureGenerateResponse> responseList = new ArrayList<>();

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

	@PrePersist
	public void prePersist() {
		if (this.matchToAdmin == null) {
			this.matchToAdmin = false;
		}
	}

	@Builder
	public PictureGenerateRequest(User requester, String prompt, CameraAngle cameraAngle, ShotCoverage shotCoverage,
		PictureRatio pictureRatio,
		PicturePose picturePose, List<PictureUserFace> userFacePictureList, String promptAdvanced) {
		this.requester = requester;
		this.prompt = prompt;
		this.promptAdvanced = promptAdvanced;
		this.picturePose = picturePose;
		this.pictureGenerateRequestStatus = CREATED;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.userFacePictureList = userFacePictureList;
		this.pictureRatio = pictureRatio;
	}

	public void modify(String prompt, CameraAngle cameraAngle, ShotCoverage shotCoverage, PictureRatio pictureRatio,
		PicturePose picturePose,
		List<PictureUserFace> pictureUserFaceList) {
		this.prompt = prompt;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.picturePose = picturePose;
		this.userFacePictureList = pictureUserFaceList;
	}

	public void assignToCreator(Creator creator) {
		if (this.pictureGenerateRequestStatus != CREATED) {
			log.error(" 이미 진행중인 작업에 대해 비 정상적인 매칭");
			return;
		}
		this.pictureGenerateRequestStatus = ASSIGNING;
		this.creator = creator;
		this.matchToAdmin = false;
	}

	public void acceptByCreator() {
		if (LocalDateTime.now().isAfter(this.getModifiedAt().plusMinutes(DateTimeUtil.ACCEPTABLE_TIME_MINUTE))) {
			throw ExpectedException.withLogging(ResponseCode.SubmitBlockedDueToPictureGenerateResponseIsExpired);
		}
		this.requester.addRequestCount();
		this.pictureGenerateRequestStatus = IN_PROGRESS;
	}

	public void assignToAdmin(Creator creator) {
		this.requester.addRequestCount();
		this.creator = creator;
		this.pictureGenerateRequestStatus = MATCH_TO_ADMIN;
		this.matchToAdmin = true;
	}

	public void rejectByCreator() {
		this.pictureGenerateRequestStatus = CREATED;
	}

	public void submittedByAdmin() {
		this.pictureGenerateRequestStatus = AWAIT_USER_VERIFICATION;
	}

	public void userVerified() {
		this.pictureGenerateRequestStatus = COMPLETED;
	}

	public void canceled() {
		this.pictureGenerateRequestStatus = CANCELED;
	}

	public void reported() {
		this.pictureGenerateRequestStatus = REPORTED;
	}

	public void addPGRES(PictureGenerateResponse newPGRES) {
		this.responseList.add(newPGRES);
	}

	public void deletePGRES(PictureGenerateResponse pictureGenerateResponse) {
		this.responseList.remove(pictureGenerateResponse);
	}

	public void userConfirmedCancellation() {
		this.pictureGenerateRequestStatus = CANCELED_CONFIRMED;
	}
}
