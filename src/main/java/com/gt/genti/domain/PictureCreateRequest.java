package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.ShotCoverage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureCreateRequest extends BaseTimeEntity {
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "requester_id")
	User requester;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	User creator;

	@OneToOne
	@JoinColumn(name = "response_id")
	PictureCreateResponse response;
	String prompt;

	@OneToMany
	@JoinColumn(name = "picture_id")
	List<Picture> facePictureList;

	@OneToOne
	@JoinColumn(name = "picture_id")
	Picture posePicture;

	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;

	Boolean success;
}
