package com.gt.genti.auth.social;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gt.genti.auth.dto.request.KakaoAccessTokenDto;
import com.gt.genti.auth.dto.request.KakaoAuthorizationCodeDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.dto.response.SocialWebLoginResponse;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.openfeign.kakao.client.KakaoApiClient;
import com.gt.genti.openfeign.kakao.client.KakaoAuthApiClient;
import com.gt.genti.openfeign.kakao.client.KakaoUserUnlinkResponseDto;
import com.gt.genti.openfeign.kakao.dto.response.KakaoTokenResponse;
import com.gt.genti.openfeign.kakao.dto.response.KakaoUserResponse;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserSignUpEventPublisher;
import com.gt.genti.util.RandomUtil;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthStrategy {

	@Value("${kakao.client-id}")
	private String kakaoClientId;
	@Value("${kakao.redirect-uri}")
	private String kakaoRedirectUri;
	@Value("${kakao.client-secret}")
	private String kakaoClientSecret;
	@Value("${kakao.admin-key}")
	private String kakaoAdminKey;

	private final String ACCESS_TOKEN_PREFIX = "Bearer ";
	private final String ADMIN_TOKEN_PREFIX = "KakaoAK ";

	private final KakaoAuthApiClient kakaoAuthApiClient;
	private final KakaoApiClient kakaoApiClient;

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserSignUpEventPublisher userSignUpEventPublisher;

	public String getAuthUri() {
		Map<String, Object> params = new HashMap<>();
		params.put("client_id", kakaoClientId);
		params.put("redirect_uri", kakaoRedirectUri);
		params.put("response_type", "code");

		String paramStr = params.entrySet().stream()
			.map(param -> param.getKey() + "=" + param.getValue())
			.collect(Collectors.joining("&"));
		return "https://kauth.kakao.com/oauth/authorize?" + paramStr;
	}

	@Transactional
	public SocialWebLoginResponse webLogin(KakaoAuthorizationCodeDto request) {
		KakaoTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
			"authorization_code",
			kakaoClientId,
			kakaoClientSecret,
			kakaoRedirectUri,
			request.getAuthorizationCode()
		);

		OauthPlatform oauthPlatform = OauthPlatform.KAKAO;
		String accessToken = tokenResponse.accessToken();
		return getUserInfo(oauthPlatform, accessToken);
	}

	public SocialWebLoginResponse tokenLogin(final KakaoAccessTokenDto request) {
		return getUserInfo(OauthPlatform.KAKAO, request.getAccessToken());
	}

	private SocialWebLoginResponse getUserInfo(OauthPlatform oauthPlatform, String accessToken) {
		KakaoUserResponse userResponse = kakaoApiClient.getUserInformation(
			ACCESS_TOKEN_PREFIX + accessToken);
		Optional<User> findUser = userRepository.findUserBySocialId(userResponse.id());
		User user;
		boolean isNewUser = false;
		if (isNewUser(findUser)) {
			User newUser = userRepository.save(User.builderWithSignIn()
				.socialId(userResponse.id())
				.oauthPlatform(oauthPlatform)
				.username(userResponse.kakaoAccount().name())
				.nickname(RandomUtil.generateRandomNickname())
				.email(userResponse.kakaoAccount().email())
				.build());
			user = newUser;
			isNewUser = true;
			userSignUpEventPublisher.publishSignUpEvent(newUser);
		} else {
			user = findUser.get();
			user.resetDeleteAt();
		}
		user.login();
		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
			.userId(user.getId().toString())
			.role(user.getUserRole().getRoles())
			.build();
		OauthJwtResponse oauthJwtResponse = OauthJwtResponse.of(
			jwtTokenProvider.generateAccessToken(tokenGenerateCommand),
			jwtTokenProvider.generateRefreshToken(tokenGenerateCommand), user.getUserRole().getStringValue());
		return SocialWebLoginResponse.of(user.getId(), user.getUsername(), user.getEmail(), isNewUser, oauthJwtResponse);
	}

	public void unlink(String userSocialId) {
		Long socialId;
		try{
			socialId = Long.parseLong(userSocialId);
		} catch (NumberFormatException e){
			throw ExpectedException.withLogging(ResponseCode.KakaoSocialIdNotValid, userSocialId);
		}
		try{

			RestTemplate restTemplate = new RestTemplateBuilder().build();
			String url = "https://kapi.kakao.com/v1/user/unlink";

			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("target_id_type", "user_id");
			params.add("target_id", userSocialId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", ADMIN_TOKEN_PREFIX + kakaoAdminKey);
			// headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
			ResponseEntity<KakaoUserUnlinkResponseDto> response = restTemplate.postForEntity(url, httpEntity, KakaoUserUnlinkResponseDto.class);
			socialId = response.getBody().getTarget_id();
		} catch (FeignException.FeignClientException e){
			log.info(e.getMessage(), e);
			log.info(e.getMessage());
			throw ExpectedException.withLogging(ResponseCode.UnHandledException, e.getMessage());
		}
	}
}
