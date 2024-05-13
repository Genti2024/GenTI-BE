package com.gt.genti.command;

import com.gt.genti.domain.PictureGenerateResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePictureCreatedByCreatorCommand {
	String url;
	PictureGenerateResponse pictureGenerateResponse;
	Long uploadedBy;
}
