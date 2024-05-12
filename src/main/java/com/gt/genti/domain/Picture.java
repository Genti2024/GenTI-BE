package com.gt.genti.domain;

import com.gt.genti.dto.CommonPictureResponseDto;

public interface Picture {
	Long getId();
	String getUrl();
	CommonPictureResponseDto mapToCommonResponse();
}
