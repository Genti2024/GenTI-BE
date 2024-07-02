package com.gt.genti.discord.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public final class Field {
	private final String name;
	private final String value;
	private final boolean inline;
}
