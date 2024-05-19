package com.gt.genti.command;

import com.gt.genti.domain.PictureGenerateResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePictureCreatedByCreatorCommand {
	String url;
	PictureGenerateResponse pictureGenerateResponse;
	Long userId;

	@Builder
	public CreatePictureCreatedByCreatorCommand(String url, PictureGenerateResponse pictureGenerateResponse,
		Long userId) {
		this.url = url;
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.userId = userId;
	}
}
