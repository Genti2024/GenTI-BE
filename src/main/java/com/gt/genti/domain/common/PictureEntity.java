package com.gt.genti.domain.common;

import com.gt.genti.domain.Picture;
import com.gt.genti.dto.CommonPictureResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class PictureEntity extends BaseTimeEntity implements Picture {

	@Column(name = "uploaded_by")
	Long uploadedBy;
	@Override
	public CommonPictureResponseDto mapToCommonResponse() {
		return new CommonPictureResponseDto(this.getId(), this.getUrl());
	}

}
