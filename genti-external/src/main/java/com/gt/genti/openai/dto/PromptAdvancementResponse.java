package com.gt.genti.openai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;

@Setter
public class PromptAdvancementResponse {

	private List<Choice> choices;

	@Setter
	public static class Choice {
		private Message message;

		@Setter
		public static class Message {
			private String content;

			@JsonProperty("content")
			public String getContent() {
				return content;
			}

		}

		@JsonProperty("message")
		public Message getMessage() {
			return message;
		}

	}

	@JsonProperty("choices")
	public List<Choice> getChoices() {
		return choices;
	}

}


