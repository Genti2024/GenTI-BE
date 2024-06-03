package com.gt.genti.command;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreatePictureCompletedCommand extends CommonPictureSaveCommand {
	PictureGenerateResponse pictureGenerateResponse;

}
