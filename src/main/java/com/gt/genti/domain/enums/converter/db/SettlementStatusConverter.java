package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.SettlementStatus;

import jakarta.persistence.Converter;

@Converter
public class SettlementStatusConverter extends DefaultEnumDBConverter<SettlementStatus> {

	public SettlementStatusConverter() {
		super(SettlementStatus.class);
	}

}
