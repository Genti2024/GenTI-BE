package com.gt.genti.auth.dto.response;

import com.gt.genti.constants.JWTConstants;
import com.gt.genti.user.model.UserRole;

import java.util.List;

public record KakaoJwtResponse(
    String accessToken,
    String refreshToken,
    String role
) {
    public static KakaoJwtResponse of(String accessToken, String refreshToken, String role) {
        return new KakaoJwtResponse(
                accessToken != null ? JWTConstants.JWT_PREFIX + accessToken : null,
                refreshToken != null ? JWTConstants.JWT_PREFIX + refreshToken : null,
                role
        );
    }
}
