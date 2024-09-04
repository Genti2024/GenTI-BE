package com.gt.genti.discord;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.gt.genti.discord.model.DiscordMessageJson;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscordMessageSender {

	public static void sendToDiscord(String urlString, DiscordMessageJson json) throws
		IOException {
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.addRequestProperty("Content-Type", "application/json");
		connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");

		try (OutputStream stream = connection.getOutputStream()) {
			stream.write(json.toString().getBytes());
			stream.flush();

			connection.getInputStream().close();
			connection.disconnect();

		} catch (Exception e) {
			throw ExpectedException.withLogging(ResponseCode.DiscordIOException, "");
		}
	}

}
