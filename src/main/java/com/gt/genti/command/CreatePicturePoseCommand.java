package com.gt.genti.command;

import com.gt.genti.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePicturePoseCommand {
	String url;
	User user;

	@Builder
	public CreatePicturePoseCommand(String url, User user) {
		this.url = url;
		this.user = user;
	}
}
