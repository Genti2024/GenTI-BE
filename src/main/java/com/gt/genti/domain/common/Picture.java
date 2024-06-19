package com.gt.genti.domain.common;

import com.gt.genti.dto.common.response.CommonPictureResponseDto;

public interface Picture {
	Long getId();
	String getKey();
	CommonPictureResponseDto mapToCommonResponse();
}
