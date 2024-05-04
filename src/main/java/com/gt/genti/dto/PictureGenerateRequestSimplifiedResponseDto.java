package com.gt.genti.dto;

import com.gt.genti.domain.enums.RequestStatus;

public class PictureGenerateRequestSimplifiedResponseDto {
	Long id;
	String prompt;
	String url;
	RequestStatus requestStatus;
}
