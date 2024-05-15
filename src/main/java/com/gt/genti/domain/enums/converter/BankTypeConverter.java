package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.BankType;
import com.gt.genti.domain.enums.CameraAngle;

import jakarta.persistence.Converter;

@Converter
public class BankTypeConverter extends DefaultStringAttributeConverter<BankType> {

	public BankTypeConverter() {
		super(BankType.class);
	}

}
