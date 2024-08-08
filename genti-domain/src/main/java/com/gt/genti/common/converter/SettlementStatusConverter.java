package com.gt.genti.common.converter;

import com.gt.genti.settlement.model.SettlementStatus;

import jakarta.persistence.Converter;

@Converter
public class SettlementStatusConverter extends DefaultEnumDBConverter<SettlementStatus> {

	public SettlementStatusConverter() {
		super(SettlementStatus.class);
	}

}
