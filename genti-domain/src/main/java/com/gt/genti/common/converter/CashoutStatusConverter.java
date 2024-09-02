package com.gt.genti.common.converter;

import com.gt.genti.cashout.model.CashoutStatus;

import jakarta.persistence.Converter;

@Converter
public class CashoutStatusConverter extends DefaultEnumDBConverter<CashoutStatus> {

	public CashoutStatusConverter() {
		super(CashoutStatus.class);
	}

}