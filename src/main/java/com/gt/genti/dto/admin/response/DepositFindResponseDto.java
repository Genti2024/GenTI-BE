package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.Deposit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
public class DepositFindResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "nowAmount")
	Long nowAmount;

	public DepositFindResponseDto(Deposit deposit) {
		this.id = deposit.getId();
		this.nowAmount = deposit.getNowAmount();
	}
}
