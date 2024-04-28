package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.RequestStatus;

import jakarta.persistence.Converter;

@Converter
public class RequestStatusConverter extends DefaultStringAttributeConverter<RequestStatus> {

	public RequestStatusConverter() {
		super(RequestStatus.class);
	}

}