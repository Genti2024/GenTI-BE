package com.gt.genti.creator.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "공급자 작업가능상태 수정 요청 dto")
public class CreatorStatusUpdateRequestDto {
	@NotNull
	@Schema(description = "변경하고자 하는 작업가능여부")
	Boolean workable;
}
