package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.PictureRatio;

import jakarta.persistence.Converter;

@Converter
public class PictureRatioConverter extends DefaultStringAttributeConverter<PictureRatio> {

	public PictureRatioConverter() {
		super(PictureRatio.class);
	}

}
