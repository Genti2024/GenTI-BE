package com.gt.genti.withdraw.dto.response;

import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "어드민의 출금 요청 처리 결과 응답 dto")
@Getter
@NoArgsConstructor
public class WithdrawCompletionResponseDto {
	@Schema(description = "출금요청 DB Id", example = "1")
	Long withdrawRequestId;
	@Schema(description = "출금요청의 상태", example = "출금 진행중")
	WithdrawRequestStatus status;
	@Schema(description = "작업한 어드민 이름", example = "로빈")
	String modifiedBy;

	@Builder
	public WithdrawCompletionResponseDto(Long withdrawRequestId, WithdrawRequestStatus status, String modifiedBy) {
		this.withdrawRequestId = withdrawRequestId;
		this.status = status;
		this.modifiedBy = modifiedBy;
	}
}
