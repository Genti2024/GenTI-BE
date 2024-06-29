package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.creator.model.BankType;

import jakarta.persistence.Converter;

@Converter
public class BankTypeConverter extends DefaultEnumDBConverter<BankType> {

	public BankTypeConverter() {
		super(BankType.class);
	}

}
