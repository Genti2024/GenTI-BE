package com.gt.genti.dto.admin.request;

import com.gt.genti.command.admin.ExampleSaveCommand;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.other.valid.ValidEnum;
import com.gt.genti.other.valid.ValidKey;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExampleSaveRequestDto {
	@ValidKey
	String key;
	@NotBlank
	String prompt;
	@ValidEnum(PictureRatio.class)
	String pictureRatio;

	public ExampleSaveCommand toCommand() {
		return ExampleSaveCommand.builder()
			.key(key)
			.prompt(prompt)
			.pictureRatio(PictureRatio.valueOf(pictureRatio))
			.build();
	}
}
