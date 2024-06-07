package com.gt.genti.dto.creator.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatorStatusUpdateRequestDto {
	@NotNull
	Boolean workable;
}
