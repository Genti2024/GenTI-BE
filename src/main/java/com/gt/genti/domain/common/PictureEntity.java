package com.gt.genti.domain.common;

import com.gt.genti.domain.Picture;
import com.gt.genti.domain.User;
import com.gt.genti.dto.CommonPictureUrlResponseDto;

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
	User uploadedBy;

	@Override
	public CommonPictureUrlResponseDto mapToCommonResponse() {
		return new CommonPictureUrlResponseDto(this.getId(), this.getUrl());
	}

}
