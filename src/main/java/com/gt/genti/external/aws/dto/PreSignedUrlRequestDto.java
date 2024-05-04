package com.gt.genti.external.aws.dto;

import com.gt.genti.dto.enums.FileType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreSignedUrlRequestDto {
	FileType fileType;
	String fileName;

	@Builder
	public PreSignedUrlRequestDto(FileType fileType, String fileName) {
		this.fileType = fileType;
		this.fileName = fileName;
	}
}