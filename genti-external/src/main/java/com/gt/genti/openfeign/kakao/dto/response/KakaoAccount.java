package com.gt.genti.openfeign.kakao.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAccount(
        KakaoUserProfile profile,
		String name,
		String email,
		String birthyear,
		String birthday
) {
}