package com.gt.genti.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportSaveRequestDto {
	Long pictureGenerateResponseId;
	String content;
}
