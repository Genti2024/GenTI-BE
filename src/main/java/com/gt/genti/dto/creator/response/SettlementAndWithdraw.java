package com.gt.genti.dto.creator.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import com.gt.genti.domain.enums.WithdrawRequestStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SettlementAndWithdraw {
	Long id;
	Boolean isSettlement;
	Long amount;
	LocalDateTime createdAt;
	WithdrawRequestStatus status;

	public SettlementAndWithdraw(Map<String, Object> attributes) {
		this.id = (Long)attributes.get("id");
		this.isSettlement = (Boolean)attributes.get("isSettlement");
		this.amount = (Long)attributes.get("amount");
		this.createdAt = (LocalDateTime)attributes.get("createdAt");
		this.status = WithdrawRequestStatus.valueOf((String)attributes.get("status"));
	}

	public SettlementAndWithdraw(Object[] o) {
		this.id = (Long)o[0];
		if ((Long)o[1] == 1L) {
			this.isSettlement = true;
		} else {
			this.isSettlement = false;
		}
		this.amount = (Long)o[2];
		this.createdAt = ((Timestamp)o[3]).toLocalDateTime();
		this.status = WithdrawRequestStatus.valueOf((String)o[4]);
	}
}
