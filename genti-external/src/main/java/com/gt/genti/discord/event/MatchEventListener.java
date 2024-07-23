package com.gt.genti.discord.event;

import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.gt.genti.discord.DiscordAppender;

import lombok.RequiredArgsConstructor;

@Component
@Profile({"local", "deploy"})
@RequiredArgsConstructor
public class MatchEventListener {

	private final DiscordAppender discordAppender;

	@EventListener
	public void handleMatchEvent(MatchEvent event) {
		discordAppender.matchResultAppend(
			event.summary(),
			event.matchResultList()
		);
	}

}
