package com.gt.genti.user.dto.response;

import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[User][User] 회원가입 응답 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponseDTO {
    @Schema(description = "이메일", example = "woogie@google.com")
    String email;
    @Schema(description = "소셜 로그인 타입", example = "KAKAO")
    OauthPlatform lastLoginOauthPlatform;
    @Schema(description = "닉네임", example = "다정한 모래")
    String nickname;
    @Schema(description = "태어난 년도", example = "1999")
    String birthYear;
    @Schema(description = "성별", example = "남")
    Sex sex;

    @Builder
    public SignUpResponseDTO(String email, OauthPlatform lastLoginOauthPlatform, String nickname, String birthYear, Sex sex) {
        this.email = email;
        this.lastLoginOauthPlatform = lastLoginOauthPlatform;
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.sex = sex;
    }
}
