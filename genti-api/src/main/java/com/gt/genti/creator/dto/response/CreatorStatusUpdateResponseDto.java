package com.gt.genti.creator.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Schema(name = "[Creator][Creator] 공급자 작업가능상태 변경 성공 응답 dto")
public class CreatorStatusUpdateResponseDto {
	@Schema(description = "작업가능 여부")
	Boolean workable;

	@Builder
	public CreatorStatusUpdateResponseDto(Boolean workable) {
		this.workable = workable;
	}
}
