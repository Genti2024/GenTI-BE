package com.gt.genti.dto.creator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공급자가 응답 제출 후 소요시간 및 리워드 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESSubmitByCreatorResponseDto {
	@Schema(description = "제출한 사진생성응답 id", example = "1")
	Long id;
	@Schema(description = "매칭 이후 제출까지 걸린 시간 HH:MM:SS", example = "01:23:42")
	String elapsedTime;
	@Schema(description = "작업으로 받은 보상", example = "2000")
	Long reward;

	@Builder
	public PGRESSubmitByCreatorResponseDto(String elapsedTime, Long reward) {
		this.elapsedTime = elapsedTime;
		this.reward = reward;
	}
}
