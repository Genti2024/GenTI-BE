package com.gt.genti.user.service.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.openfeign.apple.client.AppleApiClient;
import com.gt.genti.openfeign.apple.service.AppleOAuthUserProvider;
import com.gt.genti.openfeign.apple.service.AppleUserResponse;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpService;
import com.gt.genti.util.RandomUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppleOauthStrategy implements SocialLoginStrategy {

	@Value("${kakao.client-id}")
	private String kakaoClientId;
	@Value("${kakao.redirect-uri}")
	private String kakaoRedirectUri;
	@Value("${server.domain}")
	private String serverBaseUri;
	@Value("${server.port}")
	private String serverPort;
	@Value("${kakao.client-secret}")
	private String kakaoClientSecret;

	private final AppleApiClient appleApiClient;

	private final AppleOAuthUserProvider appleOAuthUserProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpService userSignUpService;

	@Override
	@Transactional
	public SocialLoginResponse login(SocialLoginRequest request) {
		AppleUserResponse userResponse = appleOAuthUserProvider.getApplePlatformMember(request.getCode());
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.getPlatformId());
		User user;
		boolean isNewUser = false;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.getPlatformId())
				.oauthPlatform(request.getOauthPlatform())
				.username(userResponse.getEmail())
				.nickname(RandomUtil.generateRandomNickname())
				.email(userResponse.getEmail())
				.build());
			user = newUser;
			isNewUser = true;
			userSignUpService.publishSignUpEvent(newUser);
		} else {
			user = findUser.get();
			user.resetDeleteAt();
		}
		user.login();
		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
			.userId(user.getId().toString())
			.role(user.getUserRole().getRoles())
			.build();
		TokenResponse token = new TokenResponse(jwtTokenProvider.generateAccessToken(tokenGenerateCommand),
			jwtTokenProvider.generateRefreshToken(tokenGenerateCommand));
		return SocialLoginResponse.of(user.getId(), user.getUsername(), isNewUser, token);
	}

	@Override
	public boolean support(String provider) {
		return provider.equals(OauthPlatform.APPLE.getStringValue());
	}

}
