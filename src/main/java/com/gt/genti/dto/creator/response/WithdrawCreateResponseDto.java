package com.gt.genti.dto.creator.response;

import com.gt.genti.domain.WithdrawRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawCreateResponseDto {
	Long id;
	Long amount;
	int count;

	public WithdrawCreateResponseDto(WithdrawRequest withdrawRequest) {
		this.id = withdrawRequest.getId();
		this.amount = withdrawRequest.getAmount();
		this.count = withdrawRequest.getTaskCount();
	}
}
