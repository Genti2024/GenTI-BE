package com.gt.genti.auth.dto.request;

import com.gt.genti.user.model.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Auth][Anonymous] 회원가입 요청 Dto", description = "생년, 성별")
public class SignUpRequestDTO {
    @NotBlank
    @Schema(example = "1999")
    String birthDate;

    @NotNull
    @Schema(example = "M")
    Sex sex;
}