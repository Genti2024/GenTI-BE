package com.gt.genti.config;

import java.util.List;

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

import com.gt.genti.constants.WhiteListConstants;
import com.gt.genti.security.GentiAuthenticationEntryPoint;
import com.gt.genti.security.JwtAuthenticationFilter;
import com.gt.genti.user.model.UserRole;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final GentiAuthenticationEntryPoint gentiAuthenticationEntryPoint;
	private final WhiteListConstants whiteListConstants;
	//TODO cors allowed origin 목록을 properties로 받아서 corsConfig에 추가하는 로직
	// edited at 2024-07-22
	// author 서병렬

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedOrigin("https://kauth.kakao.com");
		config.addAllowedOrigin("https://kapi.kakao.com");
		config.addAllowedOrigin("http://www.googleapis.com");
		config.addAllowedOrigin("https://www.googleapis.com");

		config.setAllowedOriginPatterns(List.of("*"));

		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		config.addExposedHeader("Access-Token");
		config.addExposedHeader("Refresh-Token");
		config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", config);
		source.registerCorsConfiguration("/auth/**", config);
		source.registerCorsConfiguration("/login/**", config);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
			.sessionManagement(sessionManagementConfigurer ->
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER))
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				authorizationManagerRequestMatcherRegistry
					.requestMatchers("/auth/v1/logout").authenticated()
					.requestMatchers(whiteListConstants.getSecurityWhiteArray()).permitAll())
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				authorizationManagerRequestMatcherRegistry
					.requestMatchers("/api/*/users/**").hasAuthority(UserRole.USER.getAuthority())
					.requestMatchers("/api/*/admin/**").hasAuthority(UserRole.ADMIN.getAuthority())
					.requestMatchers("/api/*/creators/**").hasAuthority(UserRole.CREATOR.getAuthority())
					.anyRequest().authenticated())
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class))
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					.authenticationEntryPoint(gentiAuthenticationEntryPoint))
			.build();
	}
}


