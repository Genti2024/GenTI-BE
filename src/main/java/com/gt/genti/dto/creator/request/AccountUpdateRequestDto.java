package com.gt.genti.dto.creator.request;

import com.gt.genti.domain.enums.BankType;
import com.gt.genti.other.util.RegexUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")
public class AccountUpdateRequestDto {

	@NotNull
	@Schema(name = "bankType")
	BankType bankType;

	@Pattern(regexp = RegexUtils.ACCOUNT_NUMBER, message = "계좌번호 형식이 올바르지 않습니다 (예시: 123-12345678-1234)")
	@Schema(name = "accountNumber")
	String accountNumber;

	@Pattern(regexp = RegexUtils.NAME_KOR_ENG, message = "이름이 올바르지 않습니다.")
	@Schema(name = "accountHolder")
	String accountHolder;
}
