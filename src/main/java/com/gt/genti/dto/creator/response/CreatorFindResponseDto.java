package com.gt.genti.dto.creator.response;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.enums.BankType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class CreatorFindResponseDto {
	@Schema(name = "workable")
	Boolean workable;
	@Schema(name = "bankType")
	BankType bankType;
	@Schema(name = "accountNumber")
	String accountNumber;
	@Schema(name = "accountHolder")
	String accountHolder;

	@Builder
	public CreatorFindResponseDto(Boolean workable, BankType bankType, String accountNumber, String accountHolder) {
		this.workable = workable;
		this.bankType = bankType;
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
	}

	public CreatorFindResponseDto(Creator creator){
		this.workable = creator.getWorkable();
		this.bankType = creator.getBankType();
		this.accountNumber = creator.getAccountNumber();
		this.accountHolder = creator.getAccountHolder();
	}
}
