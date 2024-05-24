package com.gt.genti.dto;

import com.gt.genti.domain.enums.BankType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatorInfoResponseDto {
	Boolean workable;
	BankType bankType;
	String accountNumber;
	String accountHolder;

	@Builder
	public CreatorInfoResponseDto(Boolean workable, BankType bankType, String accountNumber, String accountHolder) {
		this.workable = workable;
		this.bankType = bankType;
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
	}
}
