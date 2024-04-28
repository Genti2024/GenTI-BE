package com.gt.genti.config.auth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final UserRepository userRepository;

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		log.info("---------OAuth2UserService---------");
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String email = oAuth2User.getAttribute("email");
		String roles;

		Optional<User> optionalUser = userRepository.findByEmail(email);
		User user;
		if (optionalUser.isEmpty()) {
			// String userNameAttributeName = userRequest.getClientRegistration()
			// 	.getProviderDetails()
			// 	.getUserInfoEndpoint()
			// 	.getUserNameAttributeName();

			OAuthAttributes oauthAttributes = OAuthAttributeBuilder.of(registrationId, oAuth2User.getAttributes());
			user = User.createNewSocialUser(oauthAttributes);

			user = userRepository.save(user);
			roles = UserRole.addRole(UserRole.getAllRoles(user.getUserRole()), UserRole.OAUTH_FIRST_JOIN);
		} else {
			user = optionalUser.get();
			roles = user.getUserRole().getStringValue();
		}

		return new UserDetailsImpl(
			user, roles, oAuth2User.getAttributes());
	}

}
