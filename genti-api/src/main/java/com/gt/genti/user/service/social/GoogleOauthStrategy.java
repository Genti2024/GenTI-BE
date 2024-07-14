package com.gt.genti.user.service.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.openfeign.google.client.GoogleApiClient;
import com.gt.genti.openfeign.google.client.GoogleAuthApiClient;
import com.gt.genti.openfeign.google.dto.response.GoogleInfoResponse;
import com.gt.genti.openfeign.google.dto.response.GoogleTokenResponse;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpEventPublisher;

import lombok.RequiredArgsConstructor;

@Deprecated
@Service
@RequiredArgsConstructor
public class GoogleOauthStrategy implements SocialLoginStrategy, SocialAuthStrategy {

	@Value("${google.client-id}")
	private String googleClientId;
	@Value("${google.client-secret}")
	private String googleClientSecret;
	@Value("${google.redirect-url}")
	private String googleRedirectUrl;
	@Value("${server.domain}")
	private String serverBaseUri;
	@Value("${server.port}")
	private String serverPort;

	@Value("${google.scope}")
	private List<String> scope;

	private final GoogleAuthApiClient googleAuthApiClient;
	private final GoogleApiClient googleApiClient;

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpEventPublisher userSignUpEventPublisher;

	@Override
	public String getAuthUri() {
		Map<String, Object> params = new HashMap<>();
		params.put("client_id", googleClientId);
		params.put("redirect_uri", serverBaseUri + ":" + serverPort + googleRedirectUrl);
		params.put("response_type", "code");
		params.put("scope", String.join("%20", scope));

		String paramStr = params.entrySet().stream()
			.map(param -> param.getKey() + "=" + param.getValue())
			.collect(Collectors.joining("&"));
		return "https://accounts.google.com/o/oauth2/auth?" + paramStr;
	}

	@Override
	@Transactional
	public SocialLoginResponse login(SocialLoginRequest request) {
		GoogleTokenResponse tokenResponse = googleAuthApiClient.googleAuth(
			request.getCode(),
			googleClientId,
			googleClientSecret,
			serverBaseUri + ":" + serverPort + googleRedirectUrl,
			"authorization_code"
		);
		GoogleInfoResponse userResponse = googleApiClient.googleInfo("Bearer " + tokenResponse.accessToken());
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.sub());
		User user;
		boolean isNewUser = false;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.sub())
				.oauthPlatform(request.getOauthPlatform())
				.username(userResponse.name())
				.oauthImageUrl(userResponse.picture())
				.email(userResponse.email())
				.build());
			user = newUser;
			isNewUser = true;
			userSignUpEventPublisher.publishSignUpEvent(newUser);
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
		return SocialLoginResponse.of(user.getId(), user.getUsername(), isNewUser, token);
	}

	@Override
	public boolean support(String provider) {
		return false;
	}

}
