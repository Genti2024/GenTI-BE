package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.PictureGenerateRequestStatus;

import jakarta.persistence.Converter;

@Converter
public class RequestStatusConverter extends DefaultStringAttributeConverter<PictureGenerateRequestStatus> {

	public RequestStatusConverter() {
		super(PictureGenerateRequestStatus.class);
	}

}