package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.WithdrawRequest;
import com.gt.genti.domain.enums.WithdrawRequestStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawFindResponseDto {
	Long id;
	String requesterEmail;
	long amount;
	int taskCount;
	WithdrawRequestStatus status;

	public static WithdrawFindResponseDto of(WithdrawRequest withdrawRequest) {
		return WithdrawFindResponseDto.builder()
			.id(withdrawRequest.getId())
			.requesterEmail(withdrawRequest.getCreator().getUser().getEmail())
			.amount(withdrawRequest.getAmount())
			.status(withdrawRequest.getStatus())
			.taskCount(withdrawRequest.getTaskCount())
			.build();
	}

	@Builder
	public WithdrawFindResponseDto(Long id, String requesterEmail, long amount, int taskCount,
		WithdrawRequestStatus status) {
		this.id = id;
		this.requesterEmail = requesterEmail;
		this.amount = amount;
		this.taskCount = taskCount;
		this.status = status;
	}
}
