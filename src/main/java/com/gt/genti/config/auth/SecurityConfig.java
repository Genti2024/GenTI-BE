package com.gt.genti.config.auth;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.genti.config.handler.CommonLoginFailHandler;
import com.gt.genti.config.handler.CommonLoginSuccessHandler;
import com.gt.genti.security.JwtTokenProvider;
import com.gt.genti.security.JwtVerifyFilter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;
	public static final String oauthLoginPath = "/oauth2/login";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_MANAGER = "ROLE_MANAGER";
	public static final String ROLE_CREATOR = "ROLE_CREATOR";
	private static final String[] PRIVATE_ALLOWED_URLS = new String[] {
		"/login/testjwt",
		"/index",
		"/users/login",
		"/oauth2/login",
		"/login/**",
		"/login",
		"/error",
		"/test/**",
		"/oauth2"
	};

	public static String[] COMMON_RESOURCE_AND_ALLOWED_URL;

	@PostConstruct
	void init() {
		String[] enumPatterns = EnumSet.allOf(StaticResourceLocation.class).stream()
			.flatMap(StaticResourceLocation::getPatterns)
			.toArray(String[]::new);

		// Combine both pattern arrays
		COMMON_RESOURCE_AND_ALLOWED_URL = Stream.concat(Arrays.stream(enumPatterns),
				Arrays.stream(PRIVATE_ALLOWED_URLS))
			.toArray(String[]::new);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOriginPatterns(List.of("*"));
		//TODO pre-flight 적용 검토
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해서 CORS 설정을 적용

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommonLoginSuccessHandler commonLoginSuccessHandler() {
		return new CommonLoginSuccessHandler(jwtTokenProvider);
	}

	@Bean
	public CommonLoginFailHandler commonLoginFailHandler() {
		return new CommonLoginFailHandler();
	}

	@Bean
	public JwtVerifyFilter jwtVerifyFilter() {
		return new JwtVerifyFilter(jwtTokenProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(
			httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests.requestMatchers(COMMON_RESOURCE_AND_ALLOWED_URL).permitAll()
				.requestMatchers("/api/**").hasAuthority(ROLE_USER) // not use hasRole
				.anyRequest().authenticated()
		);

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.NEVER));

		http.addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);

		http.oauth2Login(httpSecurityOAuth2LoginConfigurer ->
			httpSecurityOAuth2LoginConfigurer.loginPage(oauthLoginPath)
				.successHandler(commonLoginSuccessHandler())
				.failureHandler(commonLoginFailHandler())
				.userInfoEndpoint(userInfoEndpointConfig ->
					userInfoEndpointConfig.userService(customOAuth2UserService)));

		return http.build();
	}
}


