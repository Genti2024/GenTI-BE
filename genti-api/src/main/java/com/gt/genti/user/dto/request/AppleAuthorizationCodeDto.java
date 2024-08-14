package com.gt.genti.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppleAuthorizationCodeDto {
	@NotBlank
	@Schema(description = "애플 로그인시 받는 authorization_code", example = "eyasfekeje.sfesjlfk")
	String authorizationCode;
}
