package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.ShotCoverage;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter

public class ShotCoverageConverter implements AttributeConverter<ShotCoverage, String> {

	@Override
	public String convertToDatabaseColumn(ShotCoverage enumValue) {
		return enumValue.getStringValue();
	}

	@Override
	public ShotCoverage convertToEntityAttribute(String value) {
		for (ShotCoverage shotCoverage : ShotCoverage.values()) {
			if (shotCoverage.getStringValue().equals(value)) {
				return shotCoverage;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
	}
}
