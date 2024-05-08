package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

import jakarta.persistence.Converter;

@Converter
public class PictureGenerateResponseStatusConverter extends DefaultStringAttributeConverter<PictureGenerateResponseStatus> {

	public PictureGenerateResponseStatusConverter() {
		super(PictureGenerateResponseStatus.class);
	}

}
