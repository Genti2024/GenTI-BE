package com.gt.genti.config;

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
import com.gt.genti.security.CustomSecurityFilterExceptionHandler;
import com.gt.genti.security.JwtAuthenticationFilter;
import com.gt.genti.user.model.UserRole;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	// private final ServletExceptionFilter jwtExceptionFilter;
	private final CustomSecurityFilterExceptionHandler customSecurityFilterExceptionHandler;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		// config.addAllowedOrigin("http://localhost:8080");
		// config.addAllowedOrigin("https://kauth.kakao.com");
		// config.addAllowedOrigin("https://kapi.kakao.com");
		// config.addAllowedOrigin("http://www.googleapis.com");
		// config.addAllowedOrigin("https://www.googleapis.com");

		// config.addAllowedOrigin(serverIp);
		// config.addAllowedOrigin(serverDomain);
		// config.addExposedHeader("Authorization");

		// config.setAllowedOriginPatterns(List.of("*"));
		//TODO pre-flight 적용 검토
		// config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		// config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		config.setAllowCredentials(true);

		config.addAllowedOriginPattern("/**"); // 다른 와일드카드 도메인
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
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
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				authorizationManagerRequestMatcherRegistry.requestMatchers(WhiteListConstants.SECURITY_WHITE_LIST)
					.permitAll())
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				authorizationManagerRequestMatcherRegistry
					.requestMatchers("/*").permitAll()
					.requestMatchers("/api/*/users/").hasAuthority(UserRole.USER.getAuthority()) // not use hasRole
					.requestMatchers("/api/*/admin/**").hasAuthority(UserRole.ADMIN.getAuthority())
					.requestMatchers("/api/*/creators/**").hasAuthority(UserRole.CREATOR.getAuthority())
					.anyRequest().authenticated())
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
				http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class))
			.exceptionHandling(handler -> handler.authenticationEntryPoint(customSecurityFilterExceptionHandler))
			.build();
	}
}


