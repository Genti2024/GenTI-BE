package com.gt.genti.dto.creator.response;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class SettlementAndWithdrawPageResponseDto {

	@Schema(name = "balance")
	Long balance;
	@Schema(name = "settlementAndWithdrawPage")
	Page<SettlementAndWithdraw> settlementAndWithdrawPage;

	public SettlementAndWithdrawPageResponseDto(Long balance, Page<SettlementAndWithdraw> settlementAndWithdrawPage) {
		this.balance = balance;
		this.settlementAndWithdrawPage = settlementAndWithdrawPage;
	}
}
