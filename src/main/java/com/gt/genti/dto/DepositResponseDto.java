package com.gt.genti.dto;

import com.gt.genti.domain.Deposit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositResponseDto {
	Long id;
	Long depositAmount;

	public DepositResponseDto(Deposit deposit) {
		this.id = deposit.getId();
		this.depositAmount = deposit.getDepositAmount();
	}
}
