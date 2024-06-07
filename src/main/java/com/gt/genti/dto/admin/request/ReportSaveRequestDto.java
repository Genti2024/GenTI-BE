package com.gt.genti.dto.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportSaveRequestDto {
	@NotNull
	Long pictureGenerateResponseId;
	@NotBlank
	String content;
}
