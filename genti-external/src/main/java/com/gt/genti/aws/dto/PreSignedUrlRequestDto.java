package com.gt.genti.aws.dto;

import com.gt.genti.aws.FileType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreSignedUrlRequestDto {

	@NotNull
	FileType fileType;
	String fileName;

	@Builder
	public PreSignedUrlRequestDto(FileType fileType, String fileName) {
		this.fileType = fileType;
		this.fileName = fileName;
	}
}
