package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShotCoverage {
	FACE("얼굴만 클로즈업"),
	UPPER_BODY("허리 위로 촬영"),
	KNEE_UP("허리 위로 촬영"),
	FULL_BODY("전신 촬영");

	private final String coverage;

}
