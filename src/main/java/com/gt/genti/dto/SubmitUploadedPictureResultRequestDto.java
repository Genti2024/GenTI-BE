package com.gt.genti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitUploadedPictureResultRequestDto {
	private Long pictureGenerateResponseId;
	private String uploadedUrl;
	private PostDetailResponseDto success;
}
