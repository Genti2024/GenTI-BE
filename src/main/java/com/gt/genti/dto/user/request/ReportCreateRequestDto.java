package com.gt.genti.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCreateRequestDto {
	@NotNull
	Long pictureGenerateResponseId;
	@NotBlank
	String content;
}
