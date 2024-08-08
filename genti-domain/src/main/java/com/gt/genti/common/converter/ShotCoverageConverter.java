package com.gt.genti.common.converter;

import com.gt.genti.picturegeneraterequest.model.ShotCoverage;

import jakarta.persistence.Converter;

@Converter

public class ShotCoverageConverter extends DefaultEnumDBConverter<ShotCoverage> {

	public ShotCoverageConverter() {
		super(ShotCoverage.class);
	}

}
