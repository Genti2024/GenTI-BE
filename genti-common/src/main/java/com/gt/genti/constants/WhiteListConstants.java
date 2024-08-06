package com.gt.genti.constants;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
public class WhiteListConstants {
	@Value("${management.endpoints.web.base-path}")
	private String monitoringPath;

	@Getter
	private String[] filterWhiteArray;
	@Getter
	private String[] securtiyWhiteArray;
	@PostConstruct
	void postConstruct() {
		this.filterWhiteList.add(monitoringPath + "/**");
		this.securityWhiteList.add(monitoringPath + "/**");
		filterWhiteArray = filterWhiteList.toArray(String[]::new);
		securtiyWhiteArray = securityWhiteList.toArray(String[]::new);
	}

	private List<String> filterWhiteList = List.of(
		"/favicon.ico",
		"/error",
		"/auth/**",
		"/login/**",
		"/swagger-ui",
		"/swagger-ui/**",
		"/swagger-resources/**",
		"/springdoc/**",
		"/api-docs/**",
		"/v3/api-docs/**",
		"/h2-console/**",
		"/h2-console"
	);

	private List<String> securityWhiteList = List.of(
		"/favicon.ico",
		"/error",
		"/auth/**",
		"/login/**",
		"/swagger-ui",
		"/swagger-ui/**",
		"/swagger-resources/**",
		"/springdoc/**",
		"/api-docs/**",
		"/v3/api-docs/**",
		"/h2-console/**",
		"/h2-console"
	);



}
