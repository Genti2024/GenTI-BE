package com.gt.genti;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import com.gt.genti.config.TestConfig;
import com.gt.genti.openai.dto.PromptAdvancementRequestCommand;
import com.gt.genti.openai.restclient.OpenAIRestClient;
import com.gt.genti.openai.service.OpenAIService;

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
