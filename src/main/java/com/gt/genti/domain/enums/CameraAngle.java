package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CameraAngle implements com.gt.genti.domain.enums.ConvertableEnum {
	ABOVE("위에서 촬영"),
	EYE_LEVEL("같은 높이에서 촬영"),
	BELOW("아래에서 촬영");

	private final String stringValue;
}
