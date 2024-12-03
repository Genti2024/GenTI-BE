package com.gt.genti.discord.event;

import com.gt.genti.discord.DiscordAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "deploy"})
@RequiredArgsConstructor
public class PaidMatchEventListener {

    private final DiscordAppender discordAppender;

    @EventListener
    public void handlePaidMatchEvent(PaidMatchEvent event) {
        discordAppender.paidMatchResultAppend(
                event.summary(),
                event.matchResultList()
        );
    }
}