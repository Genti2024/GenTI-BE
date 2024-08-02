package com.gt.genti.user.service.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.security.PublicKey;
import java.util.Map;
import java.util.Optional;

import com.gt.genti.user.model.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.auth.dto.request.SocialAppLoginRequest;
import com.gt.genti.auth.dto.request.SocialLoginRequest;
import com.gt.genti.auth.dto.request.SocialLoginRequestImpl;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.openfeign.apple.client.AppleApiClient;
import com.gt.genti.openfeign.apple.dto.response.ApplePublicKeys;
import com.gt.genti.openfeign.apple.service.AppleClaimsValidator;
import com.gt.genti.openfeign.apple.service.AppleJwtParser;
import com.gt.genti.openfeign.apple.service.AppleUserResponse;
import com.gt.genti.openfeign.apple.service.PublicKeyGenerator;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpEventPublisher;
import com.gt.genti.util.RandomUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppleOauthStrategy implements SocialLoginStrategy {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpEventPublisher userSignUpEventPublisher;
	private final AppleJwtParser appleJwtParser;
	private final AppleApiClient appleApiClient;
	private final PublicKeyGenerator publicKeyGenerator;
	private final AppleClaimsValidator appleClaimsValidator;

	@Override
	@Transactional
	public SocialLoginResponse webLogin(SocialLoginRequest request) {
		AppleUserResponse userResponse = getApplePlatformMember(request.getCode());
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.getPlatformId());
		User user;
		boolean isNewUser = false;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.getPlatformId())
				.oauthPlatform(request.getOauthPlatform())
				.username(null)
				.nickname(RandomUtil.generateRandomNickname())
				.email(userResponse.getEmail())
				.build());
			user = newUser;
			isNewUser = true;
			userSignUpEventPublisher.publishSignUpEvent(newUser);
		} else {
			user = findUser.get();
			if (user.getUserStatus() == UserStatus.DELETED) {
				throw ExpectedException.withLogging(ResponseCode.LoginFromDeletedUser);
			}
		}
		user.login();
		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
			.userId(user.getId().toString())
			.role(user.getUserRole().getRoles())
			.build();

		OauthJwtResponse oauthJwtResponse = OauthJwtResponse.of(jwtTokenProvider.generateAccessToken(tokenGenerateCommand),
			jwtTokenProvider.generateRefreshToken(tokenGenerateCommand), user.getUserRole().getStringValue());
		return SocialLoginResponse.of(user.getId(), user.getUsername(), user.getEmail(), isNewUser, oauthJwtResponse);
	}

	@Override
	public SocialLoginResponse tokenLogin(SocialAppLoginRequest request) {
		return webLogin(SocialLoginRequestImpl.of(request.getOauthPlatform(), request.getToken()));
	}

	@Override
	public boolean support(String provider) {
		return provider.equals(OauthPlatform.APPLE.getStringValue());
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
}
