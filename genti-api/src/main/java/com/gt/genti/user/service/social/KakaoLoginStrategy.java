package com.gt.genti.user.service.social;

import static com.gt.genti.user.service.validator.UserValidator.*;
import static com.gt.genti.util.MDCUtil.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.openfeign.dto.response.kakao.KakaoTokenResponse;
import com.gt.genti.openfeign.dto.response.kakao.KakaoUserResponse;
import com.gt.genti.openfeign.kakao.KakaoApiClient;
import com.gt.genti.openfeign.kakao.KakaoAuthApiClient;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.service.UserSignUpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoLoginStrategy implements SocialLoginStrategy {

	@Value("${kakao.client-id}")
	private String kakaoClientId;
	@Value("${kakao.redirect-uri}")
	private String kakaoRedirectUri;

	private final KakaoAuthApiClient kakaoAuthApiClient;
	private final KakaoApiClient kakaoApiClient;

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpService userSignUpService;

	@Override
	@Transactional
	public SocialLoginResponse login(SocialLoginRequest request) {
		KakaoTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
			"authorization_code",
			kakaoClientId,
			get(USER_REQUEST_ORIGIN) + kakaoRedirectUri,
			request.code()
		);
		KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(
			"Bearer " + tokenResponse.accessToken());
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.id());
		User user;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.id())
				.oauthPlatform(request.oauthPlatform())
				.username(userResponse.kakaoAccount().profile().nickname())
				.imageUrl(userResponse.kakaoAccount().profile().profileImageUrl())
				.email(null)
				.build());
			user = newUser;
		} else {
			user = findUser.get();
			user.resetDeleteAt();
		}
		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
			.userId(user.getId().toString())
			.role(user.getUserRole().getRoles())
			.build();
		TokenResponse token = new TokenResponse(jwtTokenProvider.generateAccessToken(tokenGenerateCommand),
			jwtTokenProvider.generateRefreshToken(tokenGenerateCommand));
		return SocialLoginResponse.of(user.getId(), user.getUsername(), token);
	}

	@Override
	public boolean support(String provider) {
		return provider.equals("KAKAO");
	}

}
