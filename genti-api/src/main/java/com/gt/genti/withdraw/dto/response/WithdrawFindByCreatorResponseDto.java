package com.gt.genti.withdraw.dto.response;

import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공급자의 출금요청 조회 응답 dto")
@Getter
@NoArgsConstructor
public class WithdrawFindByCreatorResponseDto {
	@Schema(description = "출금요청 DB Id", example = "1")
	Long withdrawRequestId;
	@Schema(description = "출금 신청액", example = "15000")
	Long amount;
	@Schema(description = "출금된 작업 개수", example = "3")
	int taskCount;
	@Schema(description = "출금요청 상태")
	WithdrawRequestStatus withdrawRequestStatus;

	@Builder
	public WithdrawFindByCreatorResponseDto(Long withdrawRequestId, Long amount, int taskCount,
		WithdrawRequestStatus withdrawRequestStatus) {
		this.withdrawRequestId = withdrawRequestId;
		this.amount = amount;
		this.taskCount = taskCount;
		this.withdrawRequestStatus = withdrawRequestStatus;
	}
}
