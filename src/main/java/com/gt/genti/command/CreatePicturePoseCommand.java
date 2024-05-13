package com.gt.genti.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePicturePoseCommand {
	String url;
	Long uploadedBy;

	@Builder
	public CreatePicturePoseCommand(String url, Long uploadedBy) {
		this.url = url;
		this.uploadedBy = uploadedBy;
	}
}
