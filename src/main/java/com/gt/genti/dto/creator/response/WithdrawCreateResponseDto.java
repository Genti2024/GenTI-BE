package com.gt.genti.dto.creator.response;

import com.gt.genti.domain.WithdrawRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class WithdrawCreateResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "amount")
	Long amount;
	@Schema(name = "count")
	int count;

	public WithdrawCreateResponseDto(WithdrawRequest withdrawRequest) {
		this.id = withdrawRequest.getId();
		this.amount = withdrawRequest.getAmount();
		this.count = withdrawRequest.getTaskCount();
	}
}
