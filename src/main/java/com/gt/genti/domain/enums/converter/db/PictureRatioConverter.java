package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.PictureRatio;

import jakarta.persistence.Converter;

@Converter
public class PictureRatioConverter extends DefaultEnumDBConverter<PictureRatio> {

	public PictureRatioConverter() {
		super(PictureRatio.class);
	}

}
