package com.gt.genti.dto;

import com.gt.genti.domain.Deposit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositFindResponseDto {
	Long id;
	Long depositAmount;

	public DepositFindResponseDto(Deposit deposit) {
		this.id = deposit.getId();
		this.depositAmount = deposit.getDepositAmount();
	}
}
