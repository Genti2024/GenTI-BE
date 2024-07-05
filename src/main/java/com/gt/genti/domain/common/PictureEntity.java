package com.gt.genti.domain.common;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.converter.db.PictureRatioConverter;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class PictureEntity extends BaseTimeEntity implements Picture {

	@ManyToOne
	@JoinColumn(name = "uploaded_by")
	protected User uploadedBy;

	@Column(name = "`key`", nullable = false)
	protected String key;

	@Column(name = "picture_ratio")
	@Convert(converter = PictureRatioConverter.class)
	protected PictureRatio pictureRatio;

	@Override
	public CommonPictureResponseDto mapToCommonResponse() {
		return new CommonPictureResponseDto(this.getId(), this.getKey(), this.getPictureRatio(), this.getClass().getSimpleName());
	}

}
