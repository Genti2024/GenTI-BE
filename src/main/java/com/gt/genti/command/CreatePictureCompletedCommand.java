package com.gt.genti.command;

import com.gt.genti.domain.PictureGenerateResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePictureCompletedCommand {
	String url;
	PictureGenerateResponse pictureGenerateResponse;
	Long userId;

	@Builder
	public CreatePictureCompletedCommand(String url, PictureGenerateResponse pictureGenerateResponse, Long userId) {
		this.url = url;
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.userId = userId;
	}
}
