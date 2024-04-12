package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.domain.enums.converter.CameraAngleConverter;
import com.gt.genti.domain.enums.converter.ShotCoverageConverter;

import jakarta.persistence.Convert;
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
	Creator creator;

	@OneToMany(mappedBy = "request")
	List<PictureCreateResponse> responseList;
	String prompt;

	@OneToMany
	List<Picture> facePictureList;

	@OneToOne
	@JoinColumn(name = "picture_id")
	Picture posePicture;

	@Convert(converter = CameraAngleConverter.class)
	CameraAngle cameraAngle;
	@Convert(converter = ShotCoverageConverter.class)
	ShotCoverage shotCoverage;

	Boolean success;
}
