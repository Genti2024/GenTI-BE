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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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

	@Column(name = "prompt", nullable = false)
	String prompt;

	@OneToOne
	@JoinColumn(name = "pose_picture_id", nullable = false)
	PosePicture posePicture;

	@Column(name = "camera_angle", nullable = false)
	@Convert(converter = CameraAngleConverter.class)
	CameraAngle cameraAngle;

	@Column(name = "shot_coverage", nullable = false)
	@Convert(converter = ShotCoverageConverter.class)
	ShotCoverage shotCoverage;

	@Column(name = "request_status", nullable = false)
	@Convert(converter = RequestStatusConverter.class)
	RequestStatus requestStatus;

	public PictureGenerateRequest(User requester, PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto,
		PosePicture posePicture) {
		this.requester = requester;
		this.prompt = pictureGenerateRequestRequestDto.getPrompt();
		this.posePicture = posePicture;
		this.requestStatus = RequestStatus.BEFORE_WORK;
		this.cameraAngle = EnumUtil.stringToEnum(CameraAngle.class, pictureGenerateRequestRequestDto.getCameraAngle());
		this.shotCoverage = EnumUtil.stringToEnum(ShotCoverage.class, pictureGenerateRequestRequestDto.getShotCoverage());
	}

	public void modify(PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto, PosePicture posePicture) {
		this.prompt = pictureGenerateRequestModifyDto.getPrompt();
		this.posePicture = posePicture;
		this.cameraAngle = EnumUtil.stringToEnum(CameraAngle.class, pictureGenerateRequestModifyDto.getCameraAngle());
		this.shotCoverage = EnumUtil.stringToEnum(ShotCoverage.class, pictureGenerateRequestModifyDto.getShotCoverage());
	}

	public void assign(Creator creator){
		if(this.requestStatus != RequestStatus.BEFORE_WORK){
			log.error(" 이미 진행중인 작업에 대해 비 정상적인 매칭");
			return;
		}
		this.creator = creator;
	}
}
