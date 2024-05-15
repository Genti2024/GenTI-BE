package com.gt.genti.other.security;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.TokenRefreshRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtController {

	private final JwtTokenProvider jwtTokenProvider;

	@RequestMapping("/refresh")
	public Map<String, Object> refresh(@RequestHeader(name = "Authorization") String authHeader,
		@RequestBody TokenRefreshRequestDto tokenRefreshRequestDto) {

		if (authHeader == null) {
			throw new ExpectedException(ErrorCode.TOKEN_NOT_PROVIDED);
		} else if (!authHeader.startsWith(JwtConstants.JWT_PREFIX)) {
			throw new ExpectedException(ErrorCode.INVALID_TOKEN);
		}

		String receiveAccessToken = jwtTokenProvider.getTokenFromHeader(authHeader);
		String receiveRefreshToken = tokenRefreshRequestDto.getRefreshToken();

		// Access Token 의 만료 여부 확인
		if (!jwtTokenProvider.isExpired(receiveAccessToken)) {
			return Map.of("accessToken", receiveAccessToken, "refreshToken", tokenRefreshRequestDto.getRefreshToken());
		}

		// refreshToken 검증 후 새로운 토큰 생성 후 전달
		Map<String, Object> claims = jwtTokenProvider.validateToken(receiveRefreshToken);
		String newAccessToken = jwtTokenProvider.generateToken(claims, JwtConstants.ACCESS_EXP_TIME);

		String newRefreshToken = receiveRefreshToken;
		long expTime = jwtTokenProvider.tokenRemainTime((Integer)claims.get("exp"));   // Refresh Token 남은 만료 시간
		log.info("Refresh Token Remain Expire Time = {}", expTime);
		// Refresh Token 의 만료 시간이 한 시간도 남지 않은 경우
		if (expTime <= 60) {
			newRefreshToken = jwtTokenProvider.generateToken(claims, JwtConstants.REFRESH_EXP_TIME);
		}

		return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
	}
}