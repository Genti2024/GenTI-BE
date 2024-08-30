package com.gt.genti.settlementandcashout.dto.response;

import org.springframework.data.domain.Page;

import com.gt.genti.deposit.dto.response.DepositFindByCreatorResponseDto;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.settlementandcashout.model.SettlementAndCashout;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[SettlementAndCashout][Creator] 정산&출금내역 조회 페이지네이션 응답 dto", description = "정산&출금내역 리스트와 함께 잔액을 조회한다.")
@Getter
@NoArgsConstructor
public class SettlementAndCashoutPageResponseDto {

	@Schema(description = "잔액조회 dto")
	DepositFindByCreatorResponseDto deposit;
	@Schema(description = "정산&출금내역 페이지 응답")
	Page<SettlementAndCashout> settlementAndCashoutPage;

	public SettlementAndCashoutPageResponseDto(Deposit deposit,
		Page<SettlementAndCashout> settlementAndCashoutPage) {
		this.deposit = new DepositFindByCreatorResponseDto(deposit);
		this.settlementAndCashoutPage = settlementAndCashoutPage;
	}
}
