package com.gt.genti.common.converter;

import com.gt.genti.user.model.Sex;

import jakarta.persistence.Converter;

@Converter
public class SexConverter extends DefaultEnumDBConverter<Sex> {

	public SexConverter() {
		super(Sex.class);
	}

}
