package com.gt.genti.aws.dto;

import java.net.URL;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
