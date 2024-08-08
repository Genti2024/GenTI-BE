package com.gt.genti.creator.dto.response;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.model.BankType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[Creator][Admin] 어드민의 공급자 조회 응답 dto")
public class CreatorFindByAdminResponseDto {
	@Schema(description = "공급자 DB Id", example = "1")
	Long creatorId;
	@Schema(description = "작업가능상태 여부")
	Boolean workable;
	@Schema(description = "은행 명 Enum", nullable = true, example = "국민은행")
	BankType bankType;
	@Schema(description = "계좌번호", nullable = true, example = "111111-22-333333")
	String accountNumber;
	@Schema(description = "예금주 명", nullable = true, example = "김흥국")
	String accountHolder;
	@Schema(description = "누적 공급 횟수", example = "2")
	int completedTaskCount;

	public CreatorFindByAdminResponseDto(Creator creator) {
		this.creatorId = creator.getId();
		this.workable = creator.getWorkable();
		this.bankType = creator.getBankType();
		this.accountNumber = creator.getAccountNumber();
		this.accountHolder = creator.getAccountHolder();
		this.completedTaskCount = creator.getCompletedTaskCount();
	}
}
