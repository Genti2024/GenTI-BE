package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.RequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.domain.enums.converter.CameraAngleConverter;
import com.gt.genti.domain.enums.converter.EnumUtil;
import com.gt.genti.domain.enums.converter.RequestStatusConverter;
import com.gt.genti.domain.enums.converter.ShotCoverageConverter;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;

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

	@ManyToMany
	@JoinTable(name = "picture_generate_request_picture_user_face")
	List<PictureUserFace> userFacePictureList;

	@Column(name = "prompt", nullable = false)
	String prompt;

	@Column(name = "prompt_advanced")
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
	RequestStatus requestStatus;

	@Builder
	public PictureGenerateRequest(User requester, PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto,
		PicturePose picturePose, List<PictureUserFace> userFacePictureList) {
		this.requester = requester;
		this.prompt = pictureGenerateRequestRequestDto.getPrompt();
		this.picturePose = picturePose;
		this.requestStatus = RequestStatus.BEFORE_WORK;
		this.cameraAngle = pictureGenerateRequestRequestDto.getCameraAngle();
		this.shotCoverage = pictureGenerateRequestRequestDto.getShotCoverage();
		this.userFacePictureList = userFacePictureList;
	}

	public void modify(PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto, PicturePose picturePose,
		List<PictureUserFace> pictureUserFaceList) {
		this.prompt = pictureGenerateRequestModifyDto.getPrompt();
		this.cameraAngle = EnumUtil.stringToEnum(CameraAngle.class, pictureGenerateRequestModifyDto.getCameraAngle());
		this.shotCoverage = EnumUtil.stringToEnum(ShotCoverage.class,
			pictureGenerateRequestModifyDto.getShotCoverage());
		this.picturePose = picturePose;
		this.userFacePictureList = pictureUserFaceList;

	}

	public void assign(Creator creator) {
		if (this.requestStatus != RequestStatus.BEFORE_WORK) {
			log.error(" 이미 진행중인 작업에 대해 비 정상적인 매칭");
			return;
		}
		this.creator = creator;
	}
}
