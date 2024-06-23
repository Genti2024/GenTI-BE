package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.enums.WithdrawRequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class WithdrawCompletionResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "status")
	WithdrawRequestStatus status;
	@Schema(name = "modifiedByUsername")
	String modifiedByUsername;

	@Builder
	public WithdrawCompletionResponseDto(Long id, WithdrawRequestStatus status, String modifiedByUsername) {
		this.id = id;
		this.status = status;
		this.modifiedByUsername = modifiedByUsername;
	}
}
