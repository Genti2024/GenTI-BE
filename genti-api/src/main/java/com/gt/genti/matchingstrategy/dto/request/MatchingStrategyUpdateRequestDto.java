package com.gt.genti.matchingstrategy.dto.request;

import com.gt.genti.matchingstrategy.model.RequestMatchStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Deprecated
@Getter
@NoArgsConstructor
@Schema(description = "요청 - 공급자&어드민 매칭전략 변경 요청 dto")
public class MatchingStrategyUpdateRequestDto {
	@Schema(name = "requestMatchStrategy", description = "요청 - 공급자&어드민 매칭전략")
	RequestMatchStrategy requestMatchStrategy;
}
