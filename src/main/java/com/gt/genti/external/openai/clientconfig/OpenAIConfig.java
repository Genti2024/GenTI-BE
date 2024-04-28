package com.gt.genti.external.openai.clientconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.gt.genti.external.openai.restclient.OpenAIRestClient;

@Configuration
public class OpenAIConfig {
	@Value("${openai.secret-key}")
	String secretKey;

	@Bean
	public OpenAIRestClient openAIRestClient() {
		RestClient restClient = RestClient.builder()
			.baseUrl("https://api.openai.com")
			.defaultHeader("Authorization", "Bearer " + secretKey)
			.defaultHeader("Content-type", "application/json")
			.build();
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
		return factory.createClient(OpenAIRestClient.class);
	}
}
