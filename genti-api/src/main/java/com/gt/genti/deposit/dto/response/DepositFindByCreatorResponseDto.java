package com.gt.genti.deposit.dto.response;

import com.gt.genti.deposit.model.Deposit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[Deposit][Creator] 공급자 잔액 조회 by 공급자 dto")
public class DepositFindByCreatorResponseDto {
	@Schema(description = "잔액 테이블 Id", example = "1")
	Long id;
	@Schema(description = "현재 보유 금액", example = "2000")
	Long nowAmount;
	@Schema(description = "누적 획득 금액", example = "17000")
	Long totalAmount;

	public DepositFindByCreatorResponseDto(Deposit deposit) {
		this.id = deposit.getId();
		this.nowAmount = deposit.getNowAmount();
		this.totalAmount = deposit.getTotalAmount();
	}
}