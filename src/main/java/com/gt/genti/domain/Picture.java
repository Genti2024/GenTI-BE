package com.gt.genti.domain;

import com.gt.genti.dto.CommonPictureUrlResponseDto;

public interface Picture {
	Long getId();
	String getUrl();
	CommonPictureUrlResponseDto mapToCommonResponse();
}
