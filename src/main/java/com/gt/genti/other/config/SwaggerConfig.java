package com.gt.genti.other.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(
		title = "GenTi API",
		description = "1. 인증 관련 우측에 Authorize 버튼누르고 value에 발급받은 테스트용 토큰 앞에 prefix 'Bearer '를 빼고 입력해주시면 됩니다.",
		version = "v1"
	),
	servers = {@Server(url = "/", description = "Default Server URL")}
)
@Configuration
public class SwaggerConfig {

	private final String BEARER_TOKEN_PREFIX = "Bearer"; // Bearer직접 넣어서 주는걸로 결정
	private final String securityJwtName = "JWT";

	// private final String deployUrl;
	// private final String localUrl;

	// public SwaggerConfig(
	// 	@Value("${springdoc.url.deploy}") final String deployUrl,
	// 	@Value("${springdoc.url.local}") final String localUrl
	// ) {
	// 	this.deployUrl = deployUrl;
	// 	this.localUrl = localUrl;
	// }

	@Bean
	public OpenAPI openAPI() {
		// final io.swagger.v3.oas.models.servers.Server deployServer = new io.swagger.v3.oas.models.servers.Server();
		// deployServer.setUrl(deployUrl);
		// deployServer.description("운영 환경 서버 url");
		//
		// final io.swagger.v3.oas.models.servers.Server localServer = new io.swagger.v3.oas.models.servers.Server();
		// localServer.setUrl(localUrl);
		// localServer.description("로컬(BE용) 환경 서버 url");


		final SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
		Components components = new Components()
			.addSecuritySchemes(securityJwtName, new SecurityScheme()
				.name(securityJwtName)
				.type(SecurityScheme.Type.HTTP)
				.scheme(BEARER_TOKEN_PREFIX)
				.bearerFormat(securityJwtName));

		return new OpenAPI()
			.addSecurityItem(securityRequirement)
			.components(components);
	}

}