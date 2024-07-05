package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.Sex;

import jakarta.persistence.Converter;

@Converter
public class SexConverter extends DefaultEnumDBConverter<Sex> {

	public SexConverter() {
		super(Sex.class);
	}

}
