package com.gt.genti.dto.creator.response;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementAndWithdrawPageResponseDto {

	Long balance;
	Page<SettlementAndWithdraw> settlementAndWithdrawPage;

	public SettlementAndWithdrawPageResponseDto(Long balance, Page<SettlementAndWithdraw> settlementAndWithdrawPage) {
		this.balance = balance;
		this.settlementAndWithdrawPage = settlementAndWithdrawPage;
	}
}
