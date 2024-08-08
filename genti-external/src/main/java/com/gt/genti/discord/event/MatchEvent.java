package com.gt.genti.discord.event;

import java.util.List;

public record MatchEvent(String summary, List<String> matchResultList) {
	public static MatchEvent of(String summary, List<String> matchResultList) {
		return new MatchEvent(summary, matchResultList);
	}
}

