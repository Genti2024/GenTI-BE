package com.gt.genti.command;

import com.gt.genti.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePictureUserFaceCommand {
	String url;
	User user;

	@Builder
	public CreatePictureUserFaceCommand(String url, User user) {
		this.url = url;
		this.user = user;
	}
}
