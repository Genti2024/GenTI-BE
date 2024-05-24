package com.gt.genti.dto;

import com.gt.genti.domain.enums.BankType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAccountInfoRequestDto {
	BankType bankType;
	String accountNumber;
	String accountHolder;
}
