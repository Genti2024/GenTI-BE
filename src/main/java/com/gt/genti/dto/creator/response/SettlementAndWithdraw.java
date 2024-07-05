package com.gt.genti.dto.creator.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import com.gt.genti.domain.enums.WithdrawRequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "정산 및 출금내역")
@Getter
@NoArgsConstructor
public class SettlementAndWithdraw {
	@Schema(description = "정산 혹은 출금내역 DB Id", example = "1")
	Long id;
	@Schema(description = "정산 or 출금내역 구분, true : 정산내역, false : 출금내역")
	Boolean isSettlement;
	@Schema(description = "금액", example = "2000")
	Long amount;
	@Schema(description = "생성일시")
	LocalDateTime createdAt;
	@Schema(description = "" +
		"정산&출금내역의 상태, " + "<br/>" +
		"[정산내역]작업 후 출금하지 않음 : AVAILABLE" + "<br/>" +
		"[정산내역]출금요청한 정산금에 해당하는 작업인 경우 : IN_PROGRESS" + "<br/>" +
		"[정산내역&출금내역]출금 완료된 경우 : COMPLETED " + "<br/>" +
		"[출금내역] 출금 거절된 경우(현재 사용X) : REJECTED "
	)
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
