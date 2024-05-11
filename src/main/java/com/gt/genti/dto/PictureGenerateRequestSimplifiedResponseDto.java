package com.gt.genti.dto;

import com.gt.genti.domain.enums.PictureGenerateRequestStatus;

public class PictureGenerateRequestSimplifiedResponseDto {
	Long id;
	String prompt;
	String url;
	PictureGenerateRequestStatus pictureGenerateRequestStatus;
}
