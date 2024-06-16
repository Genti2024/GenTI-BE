package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.enums.WithdrawRequestStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawCompletionResponseDto {
	Long id;
	WithdrawRequestStatus status;
	String modifiedByUsername;

	@Builder
	public WithdrawCompletionResponseDto(Long id, WithdrawRequestStatus status, String modifiedByUsername) {
		this.id = id;
		this.status = status;
		this.modifiedByUsername = modifiedByUsername;
	}
}
