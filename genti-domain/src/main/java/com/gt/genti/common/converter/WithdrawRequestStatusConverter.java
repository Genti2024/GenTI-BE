package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;

import jakarta.persistence.Converter;

@Converter
public class WithdrawRequestStatusConverter extends DefaultEnumDBConverter<WithdrawRequestStatus> {

	public WithdrawRequestStatusConverter() {
		super(WithdrawRequestStatus.class);
	}

}