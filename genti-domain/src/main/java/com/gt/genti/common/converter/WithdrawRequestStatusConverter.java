package com.gt.genti.common.converter;

import com.gt.genti.withdrawrequest.model.CashoutStatus;

import jakarta.persistence.Converter;

@Converter
public class WithdrawRequestStatusConverter extends DefaultEnumDBConverter<CashoutStatus> {

	public WithdrawRequestStatusConverter() {
		super(CashoutStatus.class);
	}

}