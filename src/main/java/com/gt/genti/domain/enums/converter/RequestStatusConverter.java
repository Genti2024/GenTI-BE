package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.RequestStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RequestStatusConverter implements AttributeConverter<RequestStatus, String> {

	@Override
	public String convertToDatabaseColumn(RequestStatus enumValue) {
		return enumValue.getStringValue();
	}

	@Override
	public RequestStatus convertToEntityAttribute(String value) {
		for (RequestStatus requestStatus : RequestStatus.values()) {
			if (requestStatus.getStringValue().equals(value)) {
				return requestStatus;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
	}
}