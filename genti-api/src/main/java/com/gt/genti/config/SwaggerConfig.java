package com.gt.genti.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gt.genti.swagger.SwaggerEnumOperationCustomizer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	private final String BEARER_TOKEN_PREFIX = "Bearer"; // Bearer직접 넣어서 주는걸로 결정
	private final String securityJwtName = "JWT";

	@Value("${springdoc.url.scheme}")
	private String urlScheme;

	@Value("${springdoc.url.host}")
	private String urlHost;

	@Value("${springdoc.url.port}")
	private String urlPort;

	@Bean
	public OpenAPI openAPI() {
		String serverUrl = String.format("%s://%s:%s", urlScheme, urlHost, urlPort);
		io.swagger.v3.oas.models.servers.Server server = new Server().url(serverUrl)
			.description("Generated Server URL");

		final SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
		Components components = new Components()
			.addSecuritySchemes(securityJwtName, new SecurityScheme()
				.name(securityJwtName)
				.type(SecurityScheme.Type.HTTP)
				.scheme(BEARER_TOKEN_PREFIX)
				.bearerFormat(securityJwtName));

		return new OpenAPI()
			.addServersItem(server)
			.addSecurityItem(securityRequirement)
			.components(components);

	}

	@Bean
	public GroupedOpenApi publicApi(SwaggerEnumOperationCustomizer swaggerEnumOperationCustomizer) {
		return GroupedOpenApi.builder()
			.group("GenTI")
			.packagesToScan("com.gt.genti")
			.addOperationCustomizer(swaggerEnumOperationCustomizer)
			.build();
	}

	@Bean
	public Info info() {
		return new Info()
			.title("GenTi API")
			.description("1. 인증 관련 우측에 Authorize 버튼누르고 value에 발급받은 테스트용 토큰 앞에 prefix 'Bearer '를 빼고 입력해주시면 됩니다.")
			.version("v1");
	}
}