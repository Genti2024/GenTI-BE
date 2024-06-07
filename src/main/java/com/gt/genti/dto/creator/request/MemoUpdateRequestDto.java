package com.gt.genti.dto.creator.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoUpdateRequestDto {
	@NotBlank
	String memo;
}
