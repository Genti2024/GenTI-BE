package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

import jakarta.persistence.Converter;

@Converter
public class PictureGenerateResponseStatusConverter extends DefaultEnumDBConverter<PictureGenerateResponseStatus> {

	public PictureGenerateResponseStatusConverter() {
		super(PictureGenerateResponseStatus.class);
	}

}
