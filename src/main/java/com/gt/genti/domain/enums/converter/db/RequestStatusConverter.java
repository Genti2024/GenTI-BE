package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.PictureGenerateRequestStatus;

import jakarta.persistence.Converter;

@Converter
public class RequestStatusConverter extends DefaultEnumDBConverter<PictureGenerateRequestStatus> {

	public RequestStatusConverter() {
		super(PictureGenerateRequestStatus.class);
	}

}