package com.gt.genti.dto.creator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")
public class CreatorStatusUpdateRequestDto {
	@NotNull
	@Schema(name = "workable")
	Boolean workable;
}
