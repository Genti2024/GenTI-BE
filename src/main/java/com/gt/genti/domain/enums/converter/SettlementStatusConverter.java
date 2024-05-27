package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.SettlementStatus;

import jakarta.persistence.Converter;

@Converter
public class SettlementStatusConverter extends DefaultStringAttributeConverter<SettlementStatus> {

	public SettlementStatusConverter() {
		super(SettlementStatus.class);
	}

}
