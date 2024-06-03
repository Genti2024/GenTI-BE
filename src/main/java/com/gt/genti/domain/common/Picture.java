package com.gt.genti.domain.common;

import com.gt.genti.dto.CommonPictureUrlResponseDto;

public interface Picture {
	Long getId();
	String getKey();
	CommonPictureUrlResponseDto mapToCommonResponse();
}
