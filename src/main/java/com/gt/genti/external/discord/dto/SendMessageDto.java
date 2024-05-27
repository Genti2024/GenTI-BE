package com.gt.genti.external.discord.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDto {

	@JsonProperty("username")
	private String username;
	@JsonProperty("content")
	private String content;

	public static SendMessageDto getAdminMessage(String content) {
		SendMessageDto sendMessageDto = new SendMessageDto();
		sendMessageDto.setUsername("admin-alarm");
		sendMessageDto.setContent(content);
		return sendMessageDto;
	}

	public static SendMessageDto getEventMessage(String content) {
		SendMessageDto sendMessageDto = new SendMessageDto();
		sendMessageDto.setUsername("event-alarm");
		sendMessageDto.setContent(content);
		return sendMessageDto;
	}
}




