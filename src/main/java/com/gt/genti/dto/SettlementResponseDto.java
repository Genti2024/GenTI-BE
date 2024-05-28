package com.gt.genti.dto;

import java.time.LocalDateTime;

import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.enums.SettlementStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementResponseDto {
	Long settlementId;
	LocalDateTime createdAt;
	Long reward;
	SettlementStatus settlementStatus;

	public SettlementResponseDto(Settlement settlement) {
		this.settlementId = settlement.getId();
		this.createdAt = settlement.getCreatedAt();
		this.reward = settlement.getReward();
		this.settlementStatus = settlement.getSettlementStatus();
	}
}
