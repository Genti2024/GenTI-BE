package com.gt.genti.responseexample.command;

import com.gt.genti.picture.PictureRatio;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleSaveCommand {
	String key;
	String prompt;
	PictureRatio pictureRatio;

	@Builder
	public ExampleSaveCommand(String key, String prompt, PictureRatio pictureRatio) {
		this.key = key;
		this.prompt = prompt;
		this.pictureRatio = pictureRatio;
	}
}
