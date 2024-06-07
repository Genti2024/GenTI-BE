package com.gt.genti.dto.creator.request;

import com.gt.genti.domain.enums.BankType;
import com.gt.genti.other.util.RegexUtils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountUpdateRequestDto {

	@NotNull
	private BankType bankType;

	@Pattern(regexp = RegexUtils.ACCOUNT_NUMBER, message = "계좌번호 형식이 올바르지 않습니다 (예시: 123-12345678-1234)")
	private String accountNumber;

	@Pattern(regexp = RegexUtils.NAME_KOR_ENG, message = "이름이 올바르지 않습니다.")
	private String accountHolder;
}
