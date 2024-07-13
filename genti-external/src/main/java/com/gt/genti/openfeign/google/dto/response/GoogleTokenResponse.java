package com.gt.genti.openfeign.google.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Deprecated
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleTokenResponse(
        String accessToken,
        String refreshToken
) {
    public static GoogleTokenResponse of(String accessToken, String refreshToken) {
        return new GoogleTokenResponse(accessToken, refreshToken);
    }
}