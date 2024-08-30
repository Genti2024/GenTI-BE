package com.gt.genti.cashout.dto.response;

import com.gt.genti.cashout.model.Cashout;
import com.gt.genti.cashout.model.CashoutStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[Cashout][Admin] 출금요청 조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor
public class CashoutFindByAdminResponseDto {
	@Schema(description = "출금요청 DB Id", example = "1")
	Long cashoutId;
	@Schema(description = "출금요청한 유저의 이메일", example = "example@gmail.com")
	String requesterEmail;
	@Schema(description = "출금 금액", example = "15000")
	long amount;
	@Schema(description = "출금 요청에 포함된 작업 수", example = "3")
	int taskCount;
	@Schema(description = "출금 요청 상태", example = "출금 완료")
	CashoutStatus status;

	public static CashoutFindByAdminResponseDto of(Cashout cashout) {
		return CashoutFindByAdminResponseDto.builder()
			.cashoutId(cashout.getId())
			.requesterEmail(cashout.getCreator().getUser().getEmail())
			.amount(cashout.getAmount())
			.status(cashout.getStatus())
			.taskCount(cashout.getTaskCount())
			.build();
	}

	@Builder
	public CashoutFindByAdminResponseDto(Long cashoutId, String requesterEmail, long amount, int taskCount,
		CashoutStatus status) {
		this.cashoutId = cashoutId;
		this.requesterEmail = requesterEmail;
		this.amount = amount;
		this.taskCount = taskCount;
		this.status = status;
	}
}
