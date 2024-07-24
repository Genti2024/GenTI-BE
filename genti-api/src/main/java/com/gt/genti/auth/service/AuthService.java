package com.gt.genti.auth.service;

import static com.gt.genti.user.service.validator.UserValidator.*;

import com.gt.genti.auth.dto.response.KakaoJwtResponse;
import com.gt.genti.user.model.UserRole;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.auth.dto.request.SocialLoginRequest;
import com.gt.genti.auth.dto.request.KakaoJwtCreateRequestDTO;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpEventPublisher;
import com.gt.genti.user.service.social.SocialOauthContext;
import com.gt.genti.util.HttpRequestUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final SocialOauthContext socialOauthContext;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpEventPublisher userSignUpEventPublisher;

	public SocialLoginResponse login(final SocialLoginRequest request) {
		return socialOauthContext.doLogin(request);
	}

	public HttpHeaders getOauthRedirect(OauthPlatform oauthPlatform){
		return HttpRequestUtil.createRedirectHttpHeader(socialOauthContext.getAuthUri(oauthPlatform));
	}

	public Boolean logout(final Long userId) {
		User foundUser = getUserByUserId(userId);
		validateUserAuthorization(foundUser.getId(), userId);
		jwtTokenProvider.deleteRefreshToken(userId);
		return true;
	}

	private User getUserByUserId(final Long userId){
		return userRepository.findById(userId).orElseThrow(()-> ExpectedException.withLogging(ResponseCode.UserNotFound));
	}

	public KakaoJwtResponse createJwt(KakaoJwtCreateRequestDTO kakaoJwtCreateRequestDTO) {
		Optional<User> findUser = userRepository.findByEmail(kakaoJwtCreateRequestDTO.getEmail());
		User user;

		if (findUser.isPresent()) {
			user = findUser.get();
			user.resetDeleteAt();
		} else {
			user = User.builderWithSignIn()
					.socialId(" ")
					.oauthPlatform(OauthPlatform.KAKAO)
					.username("유저" + kakaoJwtCreateRequestDTO.getNickname())
					.nickname(kakaoJwtCreateRequestDTO.getNickname())
					.email(kakaoJwtCreateRequestDTO.getEmail())
					.build();
			User newUser = userRepository.save(user);
			userSignUpEventPublisher.publishSignUpEvent(newUser);
		}

		user.login();

		UserRole userRole = user.getUserRole();

		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
				.userId(user.getId().toString())
				.role(userRole.getRoles())
				.build();

		return KakaoJwtResponse.of(jwtTokenProvider.generateAccessToken(tokenGenerateCommand),
				jwtTokenProvider.generateRefreshToken(tokenGenerateCommand), userRole.getStringValue());
	}
}
