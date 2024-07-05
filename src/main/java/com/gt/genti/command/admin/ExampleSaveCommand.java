package com.gt.genti.command.admin;

import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.other.valid.ValidEnum;

import jakarta.validation.constraints.NotBlank;
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
