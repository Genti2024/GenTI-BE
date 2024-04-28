package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.ShotCoverage;

import jakarta.persistence.Converter;

@Converter

public class ShotCoverageConverter extends DefaultStringAttributeConverter<ShotCoverage> {

	public ShotCoverageConverter() {
		super(ShotCoverage.class);
	}

}
