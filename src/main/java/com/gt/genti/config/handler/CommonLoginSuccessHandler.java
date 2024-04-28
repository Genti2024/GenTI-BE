package com.gt.genti.config.handler;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.gt.genti.config.auth.UserDetailsImpl;
import com.gt.genti.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CommonLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final JwtTokenProvider jwtTokenProvider;
	// private final CustomOAuth2UserService customOAuth2UserService;

	@Value("${spring.security.oauth2.redirect-url}")
	private String redirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		log.info("--------------------------- CommonLoginSuccessHandler ---------------------------");
		OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
		UserDetailsImpl userDetails = (UserDetailsImpl)oAuth2AuthenticationToken.getPrincipal(); //
		String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(); // oauth platform
		Collection<GrantedAuthority> roles = oAuth2AuthenticationToken.getAuthorities(); // role

		//TODO redirection to FE and response header에 token 넣기
		// edited at 2024-04-27
		// author 서병렬

		// String email = oAuth2User.getAttribute("email");
		// String roles;
		//
		// Optional<User> optionalUser = userRepository.findByEmail(email);
		// User user;
		// if (optionalUser.isEmpty()) {
		// 	String userNameAttributeName = userRequest.getClientRegistration()
		// 		.getProviderDetails()
		// 		.getUserInfoEndpoint()
		// 		.getUserNameAttributeName();
		//
		// 	OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
		// 		oAuth2User.getAttributes());
		// 	user = User.createNewSocialUser(attributes);
		// 	user = userRepository.save(user);
		// 	roles = UserRole.addRole(UserRole.getAllRoles(user.getUserRole()), UserRole.OAUTH_FIRST_JOIN);
		// } else {
		// 	user = optionalUser.get();
		// 	roles = user.getUserRole().getStringValue();
		// }
		//
		// return new UserDetailsImpl(
		// 	user, roles, oAuth2User.getAttributes());
		//
		// String accessToken = jwtTokenProvider.generateToken(authentication, JwtConstants.ACCESS_EXP_TIME);
		// String refreshToken = jwtTokenProvider.generateToken(authentication, JwtConstants.REFRESH_EXP_TIME);
		//
		// Map<String, String> tokens = Map.of(
		// 	"accessToken", accessToken,
		// 	"refreshToken", refreshToken
		// );
	}
}