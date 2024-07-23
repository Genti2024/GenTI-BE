package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Auth][Anonymous] jwt 토큰 요청 dto", description = "카카오 사용자 정보")
public class KakaoJwtCreateRequestDTO {
    @NotBlank
    @Schema(example = "example@naver.com")
    private String email;

    @NotNull
    @Schema(example = "EXP")
    private String nickname;
}
