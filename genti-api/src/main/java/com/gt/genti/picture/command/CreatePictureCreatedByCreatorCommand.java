package com.gt.genti.picture.command;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreatePictureCreatedByCreatorCommand extends CommonPictureSaveCommand {
	PictureGenerateResponse pictureGenerateResponse;
}
