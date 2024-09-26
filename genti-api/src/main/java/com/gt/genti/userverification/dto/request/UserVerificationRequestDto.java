package com.gt.genti.userverification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[UserVerification][User] 사용자 본인 인증 요청 dto", description = "본인 인증 사진을 저장함")
public class UserVerificationRequestDto {

    @Schema(description = "s3 key", example = "USER_VERIFICATION_IMAGE/image.jpg")
    String key;

}
