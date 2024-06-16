package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.Deposit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositFindResponseDto {
	Long id;
	Long nowAmount;

	public DepositFindResponseDto(Deposit deposit) {
		this.id = deposit.getId();
		this.nowAmount = deposit.getNowAmount();
	}
}
