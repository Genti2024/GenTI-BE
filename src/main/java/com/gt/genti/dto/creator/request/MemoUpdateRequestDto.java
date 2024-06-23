package com.gt.genti.dto.creator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")
public class MemoUpdateRequestDto {
	@NotBlank
	@Schema(name = "memo")
	String memo;
}
