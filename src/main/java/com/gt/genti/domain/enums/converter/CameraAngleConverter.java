package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.CameraAngle;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CameraAngleConverter implements AttributeConverter<CameraAngle, String> {

	@Override
	public String convertToDatabaseColumn(CameraAngle enumValue) {
		return enumValue.getAngle();
	}

	@Override
	public CameraAngle convertToEntityAttribute(String value) {
		for (CameraAngle cameraAngle : CameraAngle.values()) {
			if (cameraAngle.getAngle().equals(value)) {
				return cameraAngle;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
	}
}
