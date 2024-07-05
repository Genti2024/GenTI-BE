package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.CameraAngle;

import jakarta.persistence.Converter;

@Converter
public class CameraAngleConverter extends DefaultEnumDBConverter<CameraAngle> {

	public CameraAngleConverter() {
		super(CameraAngle.class);
	}

}
