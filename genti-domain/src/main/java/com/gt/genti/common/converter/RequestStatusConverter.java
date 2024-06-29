package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;

import jakarta.persistence.Converter;

@Converter
public class RequestStatusConverter extends DefaultEnumDBConverter<PictureGenerateRequestStatus> {

	public RequestStatusConverter() {
		super(PictureGenerateRequestStatus.class);
	}

}