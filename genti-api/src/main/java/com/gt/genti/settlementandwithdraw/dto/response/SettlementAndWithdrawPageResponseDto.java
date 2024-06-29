package com.gt.genti.settlementandwithdraw.dto.response;

import org.springframework.data.domain.Page;

import com.gt.genti.deposit.dto.response.DepositFindByCreatorResponseDto;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.settlementanwithdraw.model.SettlementAndWithdraw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "정산&출금내역 리스트와 함께 잔액을 조회한다.")
@Getter
@NoArgsConstructor
public class SettlementAndWithdrawPageResponseDto {

	@Schema(description = "잔액조회 dto")
	DepositFindByCreatorResponseDto deposit;
	@Schema(description = "정산&출금내역 페이지 응답")
	Page<SettlementAndWithdraw> settlementAndWithdrawPage;

	public SettlementAndWithdrawPageResponseDto(Deposit deposit,
		Page<SettlementAndWithdraw> settlementAndWithdrawPage) {
		this.deposit = new DepositFindByCreatorResponseDto(deposit);
		this.settlementAndWithdrawPage = settlementAndWithdrawPage;
	}
}
