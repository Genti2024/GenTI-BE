package com.gt.genti.config.auth;

import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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

import com.gt.genti.config.handler.CommonLoginFailHandler;
import com.gt.genti.config.handler.CommonLoginSuccessHandler;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.security.controller.JwtVerifyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;

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
		return new CommonLoginSuccessHandler();
	}

	@Bean
	public CommonLoginFailHandler commonLoginFailHandler() {
		return new CommonLoginFailHandler();
	}

	// @Bean
	// public JwtVerifyFilter jwtVerifyFilter() {
	// 	return new JwtVerifyFilter();
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(
			httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(
					PathRequest.toStaticResources().atCommonLocations())
				.permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/index").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/**").permitAll()
				.requestMatchers("/users/login").permitAll()
				.requestMatchers("/oauth2/login").permitAll()
				.requestMatchers("/api/**").hasRole(UserRole.USER.getRole())
			// .anyRequest().authenticated()
		);

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.NEVER));

		// http.addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);

		http.oauth2Login(httpSecurityOAuth2LoginConfigurer ->
			httpSecurityOAuth2LoginConfigurer.loginPage("/oauth2/login")
				.successHandler(commonLoginSuccessHandler())
				.userInfoEndpoint(userInfoEndpointConfig ->
					userInfoEndpointConfig.userService(customOAuth2UserService)));

		return http.build();
	}
}

