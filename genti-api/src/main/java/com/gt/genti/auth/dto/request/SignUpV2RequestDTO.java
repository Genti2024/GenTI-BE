package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Auth][Anonymous] 회원가입V2 요청 Dto", description = "생년, 성별, 전화번호")
public class SignUpV2RequestDTO extends SignUpRequestDTO {

    @Schema(example = "010-1234-5678")
    String phoneNumber;
}
