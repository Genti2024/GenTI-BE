package com.gt.genti.dto.admin.request;

import com.gt.genti.other.valid.Key;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExampleSaveRequestDto {
	@Key
	String key;
	@NotNull
	String prompt;
}
