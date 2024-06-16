package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.WithdrawRequestStatus;

import jakarta.persistence.Converter;

@Converter
public class WithdrawRequestStatusConverter extends DefaultEnumDBConverter<WithdrawRequestStatus> {

	public WithdrawRequestStatusConverter() {
		super(WithdrawRequestStatus.class);
	}

}