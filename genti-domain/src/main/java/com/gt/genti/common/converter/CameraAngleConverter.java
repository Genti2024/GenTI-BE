package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.picturegeneraterequest.model.CameraAngle;

import jakarta.persistence.Converter;

@Converter
public class CameraAngleConverter extends DefaultEnumDBConverter<CameraAngle> {

	public CameraAngleConverter() {
		super(CameraAngle.class);
	}

}
