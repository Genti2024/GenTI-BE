package com.gt.genti.dto;

import com.gt.genti.domain.enums.BankType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountUpdateRequestDto {

	@NotNull(message = "BankType is mandatory")
	private BankType bankType;

	@NotBlank(message = "Account number is mandatory")
	private String accountNumber;

	@NotBlank(message = "Account holder is mandatory")
	private String accountHolder;

}
