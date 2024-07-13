package com.gt.genti.openfeign.apple.service;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.openfeign.apple.client.AppleApiClient;
import com.gt.genti.openfeign.apple.dto.response.ApplePublicKeys;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppleOAuthUserProvider {

	private final AppleJwtParser appleJwtParser;
	private final AppleApiClient appleApiClient;
	private final PublicKeyGenerator publicKeyGenerator;
	private final AppleClaimsValidator appleClaimsValidator;

	public AppleUserResponse getApplePlatformMember(String identityToken) {
		Map<String, String> headers = appleJwtParser.parseHeaders(identityToken);
		ApplePublicKeys applePublicKeys = appleApiClient.getApplePublicKeys();

		PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);

		Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		validateClaims(claims);
		return new AppleUserResponse(claims.getSubject(), claims.get("email", String.class));
	}

	private void validateClaims(Claims claims) {
		if (!appleClaimsValidator.isValid(claims)) {
			throw ExpectedException.withLogging(ResponseCode.AppleOauthClaimInvalid);
		}
	}
}