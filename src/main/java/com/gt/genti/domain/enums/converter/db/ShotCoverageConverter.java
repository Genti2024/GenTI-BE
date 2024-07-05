package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.ShotCoverage;

import jakarta.persistence.Converter;

@Converter

public class ShotCoverageConverter extends DefaultEnumDBConverter<ShotCoverage> {

	public ShotCoverageConverter() {
		super(ShotCoverage.class);
	}

}
