package com.gt.genti.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import com.gt.genti.external.openai.dto.PromptAdvancementRequestCommand;
import com.gt.genti.external.openai.restclient.OpenAIRestClient;
import com.gt.genti.external.openai.service.OpenAIService;
import com.gt.genti.service.config.TestConfig;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
public class OpenAITest {
	@InjectMocks
	OpenAIService openAIService;

	@Mock
	OpenAIRestClient openAIRestClient;

	@Test
	public void promptAdvanceRequestFailedBy429TooManyRequest() {
		HttpClientErrorException mock429Error = new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS);
		//given
		given(openAIRestClient.getAdvancedPrompt(any())).willThrow(mock429Error);

		//when
		String result = openAIService.getAdvancedPrompt(new PromptAdvancementRequestCommand("asdf"));

		//then
		assertNull(result);
	}
}
