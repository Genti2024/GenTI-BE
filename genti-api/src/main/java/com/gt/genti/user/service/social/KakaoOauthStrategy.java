package com.gt.genti.user.service.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.openfeign.kakao.client.KakaoApiClient;
import com.gt.genti.openfeign.kakao.client.KakaoAuthApiClient;
import com.gt.genti.openfeign.kakao.dto.response.KakaoTokenResponse;
import com.gt.genti.openfeign.kakao.dto.response.KakaoUserResponse;
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
public class KakaoOauthStrategy implements SocialLoginStrategy, SocialAuthStrategy {

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

	private final KakaoAuthApiClient kakaoAuthApiClient;
	private final KakaoApiClient kakaoApiClient;

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpService userSignUpService;

	@Override
	public String getAuthUri() {
		Map<String, Object> params = new HashMap<>();
		params.put("client_id", kakaoClientId);
		params.put("redirect_uri", serverBaseUri + ":" + serverPort + kakaoRedirectUri);
		params.put("response_type", "code");

		String paramStr = params.entrySet().stream()
			.map(param -> param.getKey() + "=" + param.getValue())
			.collect(Collectors.joining("&"));
		return "https://kauth.kakao.com/oauth/authorize?" + paramStr;
	}

	@Override
	@Transactional
	public SocialLoginResponse login(SocialLoginRequest request) {
		KakaoTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
			"authorization_code",
			kakaoClientId,
			kakaoClientSecret,
			serverBaseUri + ":" + serverPort + kakaoRedirectUri,
			request.getCode()
		);
		KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(
			"Bearer " + tokenResponse.accessToken());
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.id());
		User user;
		boolean isNewUser = false;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.id())
				.birthDate(getBirthDateStringFrom(userResponse))
				.oauthPlatform(request.getOauthPlatform())
				.username(userResponse.kakaoAccount().name())
				.nickname(RandomUtil.generateRandomNickname())
				// .oauthImageUrl(userResponse.kakaoAccount().profile().profileImageUrl())
				.email(userResponse.kakaoAccount().email())
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

	private static String getBirthDateStringFrom(KakaoUserResponse userResponse) {
		String birthDate = null;
		String birthYear = userResponse.kakaoAccount().birthyear();
		String birthday = userResponse.kakaoAccount().birthday();
		if (birthYear != null && birthday != null && birthday.length() == 4) {
			birthDate = birthYear + "-" + birthday.substring(0, 2) + "-" + birthday.substring(2, 4);
		}
		return birthDate;
	}

	@Override
	public boolean support(String provider) {
		return provider.equals(OauthPlatform.KAKAO.getStringValue());
	}

}