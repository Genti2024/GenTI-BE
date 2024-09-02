package com.gt.genti.cashout.dto.response;

import com.gt.genti.cashout.model.CashoutStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[Cashout][Admin] 출금 요청 해결(송금) 완료시 응답 dto")
@Getter
@NoArgsConstructor
public class CashoutCompletionResponseDto {
	@Schema(description = "출금요청 DB Id", example = "1")
	Long cashoutId;
	@Schema(description = "출금요청의 상태", example = "출금 진행중")
	CashoutStatus status;
	@Schema(description = "작업한 어드민 이름", example = "로빈")
	String modifiedBy;

	@Builder
	public CashoutCompletionResponseDto(Long cashoutId, CashoutStatus status, String modifiedBy) {
		this.cashoutId = cashoutId;
		this.status = status;
		this.modifiedBy = modifiedBy;
	}
}
