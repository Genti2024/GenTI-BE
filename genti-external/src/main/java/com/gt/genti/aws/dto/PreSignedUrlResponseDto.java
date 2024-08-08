package com.gt.genti.aws.dto;

import java.net.URL;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[Presigned-url][Anonymous] 사진업로드할 url 응답 dto")
public class PreSignedUrlResponseDto {
	String fileName;
	URL url;
	String s3Key;

	public PreSignedUrlResponseDto(String fileName, URL url, String s3Key) {
		this.fileName = fileName;
		this.url = url;
		this.s3Key = s3Key;
	}
}
