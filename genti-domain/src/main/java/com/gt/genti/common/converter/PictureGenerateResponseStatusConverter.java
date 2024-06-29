package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

import jakarta.persistence.Converter;

@Converter
public class PictureGenerateResponseStatusConverter extends DefaultEnumDBConverter<PictureGenerateResponseStatus> {

	public PictureGenerateResponseStatusConverter() {
		super(PictureGenerateResponseStatus.class);
	}

}
