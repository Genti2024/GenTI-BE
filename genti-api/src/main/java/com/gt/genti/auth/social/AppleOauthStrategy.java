package com.gt.genti.auth.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gt.genti.auth.dto.request.AppleAuthTokenDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.firebase.dto.request.FcmTokenSaveOrUpdateRequestDto;
import com.gt.genti.firebase.service.FcmTokenRegisterService;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.openfeign.apple.client.AppleApiClient;
import com.gt.genti.openfeign.apple.client.AppleTokenRefreshRequest;
import com.gt.genti.openfeign.apple.dto.request.AppleTokenRequest;
import com.gt.genti.openfeign.apple.dto.response.ApplePublicKeys;
import com.gt.genti.openfeign.apple.dto.response.AppleTokenRefreshResponse;
import com.gt.genti.openfeign.apple.dto.response.AppleTokenResponse;
import com.gt.genti.openfeign.apple.service.AppleClaimsValidator;
import com.gt.genti.openfeign.apple.service.AppleJwtParser;
import com.gt.genti.openfeign.apple.service.AppleUserResponse;
import com.gt.genti.openfeign.apple.service.PrivateKeyGenerator;
import com.gt.genti.openfeign.apple.service.PublicKeyGenerator;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpEventPublisher;
import com.gt.genti.util.RandomUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOauthStrategy {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpEventPublisher userSignUpEventPublisher;
	private final AppleJwtParser appleJwtParser;
	private final AppleApiClient appleApiClient;
	private final PublicKeyGenerator publicKeyGenerator;
	private final PrivateKeyGenerator privateKeyGenerator;
	private final AppleClaimsValidator appleClaimsValidator;
	private final FcmTokenRegisterService fcmTokenRegisterService;

	@Value("${apple.client-id}")
	private String appleClientId;

	@Value("${apple.key-id}")
	private String appleSignKeyId;

	@Value("${apple.team-id}")
	private String appleTeamId;

	@Transactional
	public OauthJwtResponse login(AppleAuthTokenDto request) {
		AppleUserResponse userResponse = getApplePlatformMember(request.getIdentityToken());
		AppleTokenRequest appleTokenRequest = AppleTokenRequest.of(request.getAuthorizationCode(), appleClientId,
			createClientSecret(), "authorization_code");
		AppleTokenResponse appleTokenResponse = appleApiClient.getToken(appleTokenRequest);

		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.getPlatformId());
		User user;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.getPlatformId())
				.oauthPlatform(OauthPlatform.APPLE)
				.username(null)
				.nickname(RandomUtil.generateRandomNickname())
				.email(userResponse.getEmail())
				.build());
			newUser.setAppleRefreshToken(appleTokenResponse.getRefresh_token());
			user = newUser;
			userSignUpEventPublisher.publishSignUpEvent(newUser);
		} else {
			user = findUser.get();
			if (!appleTokenResponse.getRefresh_token().equals(user.getAppleRefreshToken())) {
				user.setAppleRefreshToken(appleTokenResponse.getRefresh_token());
			}
			user.resetDeleteAt();
		}
		user.login();
		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
			.userId(user.getId().toString())
			.role(user.getUserRole().getRoles())
			.build();

		fcmTokenRegisterService.registerFcmToken(
			FcmTokenSaveOrUpdateRequestDto.of(request.getFcmToken(), user.getId()));

		return OauthJwtResponse.of(
			jwtTokenProvider.generateAccessToken(tokenGenerateCommand),
			jwtTokenProvider.generateRefreshToken(tokenGenerateCommand), user.getUserRole().getStringValue());
	}

	private AppleUserResponse getApplePlatformMember(String identityToken) {
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

	public void unlink(String refreshToken) {
		AppleTokenRefreshRequest appleTokenRefreshRequest = AppleTokenRefreshRequest.of(refreshToken, appleClientId,
			createClientSecret(), "refresh_token");
		AppleTokenRefreshResponse response = appleApiClient.refresh(appleTokenRefreshRequest);

		String accessToken = response.getAccess_token();
		if (accessToken == null) {
			log.error("asdf");
			return;
		}
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		String revokeUrl = "https://appleid.apple.com/auth/revoke";

		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", appleClientId);
		params.add("client_secret", createClientSecret());
		params.add("token", accessToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

		restTemplate.postForEntity(revokeUrl, httpEntity, String.class);
	}

	private String createClientSecret() {
		Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
		Map<String, Object> jwtHeader = new HashMap<>();
		jwtHeader.put("kid", appleSignKeyId);
		jwtHeader.put("alg", "ES256");

		PrivateKey privateKey;
		try {
			privateKey = privateKeyGenerator.getPrivateKey();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return Jwts.builder()
			.setHeaderParams(jwtHeader)
			.setIssuer(appleTeamId)
			.setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간 - UNIX 시간
			.setExpiration(expirationDate) // 만료 시간
			.setAudience("https://appleid.apple.com")
			.setSubject(appleClientId)
			.signWith(SignatureAlgorithm.ES256, privateKey)
			.compact();
	}

}
