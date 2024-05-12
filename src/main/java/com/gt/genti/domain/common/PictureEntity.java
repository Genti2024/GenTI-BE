package com.gt.genti.domain.common;

import com.gt.genti.domain.Picture;
import com.gt.genti.dto.CommonPictureResponseDto;

public abstract class PictureEntity extends BaseTimeEntity implements Picture {


	@Override
	public CommonPictureResponseDto mapToCommonResponse() {
		return new CommonPictureResponseDto(this.getId(), this.getUrl());
	}
}
