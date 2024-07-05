package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.BankType;

import jakarta.persistence.Converter;

@Converter
public class BankTypeConverter extends DefaultEnumDBConverter<BankType> {

	public BankTypeConverter() {
		super(BankType.class);
	}

}
