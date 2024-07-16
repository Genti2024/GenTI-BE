package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Schema(name = "[Auth][Anonymous] 애플 로그인 요청 dto", description = "토큰 전달")
public class AppleLoginRequestDto {
    @NotBlank
    @Schema(description = "애플로그인 성공후 받은 토큰", example = "cvoeEIrnf23.4122")
    private String token;
}