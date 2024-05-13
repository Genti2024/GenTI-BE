package com.gt.genti.command;

import com.gt.genti.domain.PictureGenerateResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePictureCompletedCommand {
	String url;
	PictureGenerateResponse pictureGenerateResponse;
	Long uploadedBy;

	@Builder
	public CreatePictureCompletedCommand(String url, PictureGenerateResponse pictureGenerateResponse, Long uploadedBy) {
		this.url = url;
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.uploadedBy = uploadedBy;
	}
}
