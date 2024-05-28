package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.BankType;
import com.gt.genti.domain.enums.Sex;

import jakarta.persistence.Converter;

@Converter
public class SexConverter extends DefaultStringAttributeConverter<Sex> {

	public SexConverter() {
		super(Sex.class);
	}

}
