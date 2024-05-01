package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.CameraAngle;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CameraAngleConverter extends DefaultStringAttributeConverter<CameraAngle> {

	public CameraAngleConverter() {
		super(CameraAngle.class);
	}

}
