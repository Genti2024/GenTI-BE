package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.RequestMatchStrategy;

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
