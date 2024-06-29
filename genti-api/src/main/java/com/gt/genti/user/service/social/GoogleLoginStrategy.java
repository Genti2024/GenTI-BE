package com.gt.genti.user.service.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.openfeign.dto.response.google.GoogleInfoResponse;
import com.gt.genti.openfeign.dto.response.google.GoogleTokenResponse;
import com.gt.genti.openfeign.google.GoogleApiClient;
import com.gt.genti.openfeign.google.GoogleAuthApiClient;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleLoginStrategy implements SocialLoginStrategy {

	@Value("${google.client-id}")
	private String googleClientId;
	@Value("${google.client-secret}")
	private String googleClientSecret;
	@Value("${google.redirect-url}")
	private String googleRedirectUrl;

	private final GoogleAuthApiClient googleAuthApiClient;
	private final GoogleApiClient googleApiClient;

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpService userSignUpService;

	@Override
	@Transactional
	public SocialLoginResponse login(SocialLoginRequest request) {
		GoogleTokenResponse tokenResponse = googleAuthApiClient.googleAuth(
			request.code(),
			googleClientId,
			googleClientSecret,
			googleRedirectUrl,
			"authorization_code"
		);
		GoogleInfoResponse userResponse = googleApiClient.googleInfo("Bearer " + tokenResponse.accessToken());
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.sub());
		User user;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.sub())
				.oauthPlatform(request.oauthPlatform())
				.username(userResponse.name())
				.imageUrl(userResponse.picture())
				.email(userResponse.email())
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
		return provider.equals("GOOGLE");
	}

}
