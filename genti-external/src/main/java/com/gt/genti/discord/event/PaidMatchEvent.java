package com.gt.genti.discord.event;

import java.util.List;

public record PaidMatchEvent(String summary, List<String> matchResultList) {

    public static PaidMatchEvent of(String summary, List<String> matchResultList) {
        return new PaidMatchEvent(summary, matchResultList);
    }
}