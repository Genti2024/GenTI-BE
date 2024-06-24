package com.gt.genti.dto.creator.request;

import com.gt.genti.domain.enums.BankType;
import com.gt.genti.other.util.RegexUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @Tag(name = "RequestDtos", description = "RequestDtos")
@Schema(description = "계좌정보 수정 요청 Dto")
@Getter
@NoArgsConstructor
public class AccountUpdateRequestDto {

	@NotNull
	@Schema(description = "은행명", example ="KB")
	BankType bankType;

	@NotNull
	@Pattern(regexp = RegexUtils.ACCOUNT_NUMBER, message = "계좌번호 형식이 올바르지 않습니다 (예시: 123-12345678-1234)")
	@Schema(name = "accountNumber", example = "111111-22-333333")
	String accountNumber;

	@NotNull
	@Pattern(regexp = RegexUtils.NAME_KOR_ENG, message = "이름이 올바르지 않습니다.")
	@Schema(name = "accountHolder", example = "김흥국")
	String accountHolder;
}
