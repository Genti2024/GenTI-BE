package com.gt.genti.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePicturePoseCommand {
	String url;
	Long userId;

	@Builder
	public CreatePicturePoseCommand(String url, Long userId) {
		this.url = url;
		this.userId = userId;
	}
}
