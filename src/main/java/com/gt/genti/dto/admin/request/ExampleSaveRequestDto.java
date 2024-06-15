package com.gt.genti.dto.admin.request;

import com.gt.genti.other.valid.ValidKey;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExampleSaveRequestDto {
	@ValidKey
	String key;
	@NotNull
	String prompt;
}
