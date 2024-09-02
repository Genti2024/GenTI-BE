package com.gt.genti.cashout.dto.response;

import com.gt.genti.cashout.model.CashoutStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[Cashout][Creator] 출금요청 조회 by 공급자 응답 dto")
@Getter
@NoArgsConstructor
public class CashoutFindByCreatorResponseDto {
	@Schema(description = "출금요청 DB Id", example = "1")
	Long cashoutId;
	@Schema(description = "출금 신청액", example = "15000")
	Long amount;
	@Schema(description = "출금된 작업 개수", example = "3")
	int taskCount;
	@Schema(description = "출금요청 상태")
	CashoutStatus cashoutStatus;

	@Builder
	public CashoutFindByCreatorResponseDto(Long cashoutId, Long amount, int taskCount,
		CashoutStatus cashoutStatus) {
		this.cashoutId = cashoutId;
		this.amount = amount;
		this.taskCount = taskCount;
		this.cashoutStatus = cashoutStatus;
	}
}
