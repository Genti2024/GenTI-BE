package com.gt.genti.picture.command;

import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;

import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class CreatePictureCompletedCommand extends CommonPictureSaveCommand {
	PictureGenerateResponse pictureGenerateResponse;
	User requester;
	PictureRatio pictureRatio;
}
