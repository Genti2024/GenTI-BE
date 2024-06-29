package com.gt.genti.withdraw.dto.response;

import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;
import com.gt.genti.withdrawrequest.model.WithdrawRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "어드민의 출금요청 조회 응답 dto")
@Getter
@NoArgsConstructor
public class WithdrawFindByAdminResponseDto {
	@Schema(description = "출금요청 DB Id", example = "1")
	Long withdrawRequestId;
	@Schema(description = "출금요청한 유저의 이메일", example = "example@gmail.com")
	String requesterEmail;
	@Schema(description = "출금 금액", example = "15000")
	long amount;
	@Schema(description = "출금 요청에 포함된 작업 수", example = "3")
	int taskCount;
	@Schema(description = "출금 요청 상태", example = "출금 완료")
	WithdrawRequestStatus status;

	public static WithdrawFindByAdminResponseDto of(WithdrawRequest withdrawRequest) {
		return WithdrawFindByAdminResponseDto.builder()
			.withdrawRequestId(withdrawRequest.getId())
			.requesterEmail(withdrawRequest.getCreator().getUser().getEmail())
			.amount(withdrawRequest.getAmount())
			.status(withdrawRequest.getStatus())
			.taskCount(withdrawRequest.getTaskCount())
			.build();
	}

	@Builder
	public WithdrawFindByAdminResponseDto(Long withdrawRequestId, String requesterEmail, long amount, int taskCount,
		WithdrawRequestStatus status) {
		this.withdrawRequestId = withdrawRequestId;
		this.requesterEmail = requesterEmail;
		this.amount = amount;
		this.taskCount = taskCount;
		this.status = status;
	}
}
