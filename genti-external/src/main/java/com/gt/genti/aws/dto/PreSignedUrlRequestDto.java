package com.gt.genti.aws.dto;

import com.gt.genti.aws.FileType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[Presigned-url][Anonymous] 사진업로드할 url 요청 dto ")
public class PreSignedUrlRequestDto {

	FileType fileType;
	String fileName;

	@Builder
	public PreSignedUrlRequestDto(FileType fileType, String fileName) {
		this.fileType = fileType;
		this.fileName = fileName;
	}
}
