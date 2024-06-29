package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.picture.PictureRatio;

import jakarta.persistence.Converter;

@Converter
public class PictureRatioConverter extends DefaultEnumDBConverter<PictureRatio> {

	public PictureRatioConverter() {
		super(PictureRatio.class);
	}

}
