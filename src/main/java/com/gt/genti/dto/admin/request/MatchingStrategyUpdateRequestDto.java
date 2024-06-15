package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.RequestMatchStrategy;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchingStrategyUpdateRequestDto {
	RequestMatchStrategy requestMatchStrategy;
}
