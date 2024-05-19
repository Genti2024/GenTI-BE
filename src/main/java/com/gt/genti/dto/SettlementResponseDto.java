package com.gt.genti.dto;

import java.time.LocalDateTime;

import com.gt.genti.domain.Settlement;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementResponseDto {
	Long settlementId;
	LocalDateTime createdAt;
	Long reward;
	Boolean withdrawn;


	public SettlementResponseDto(Settlement settlement) {
		this.settlementId = settlement.getId();
		this.createdAt = settlement.getCreatedAt();
		this.reward = settlement.getReward();
		this.withdrawn = settlement.getWithdrawn();
	}
}
