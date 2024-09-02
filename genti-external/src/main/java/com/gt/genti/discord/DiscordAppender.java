package com.gt.genti.discord;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gt.genti.discord.model.EmbedObject;
import com.gt.genti.discord.model.Field;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.util.MDCUtil;
import com.gt.genti.util.StringUtil;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Component
@RequiredArgsConstructor
public class DiscordAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	@Value("${discord.webhook-url.base}${discord.webhook-url.admin}")
	private String adminChannelUrl;
	@Value("${discord.webhook-url.base}${discord.webhook-url.event}")
	private String eventChannelUrl;
	@Value("${discord.webhook-url.base}${discord.webhook-url.error}")
	private String errorChannelUrl;
	@Value("${app.environment}")
	private String profile;
	private String username = "Error log";
	private String avatarUrl = "https://cdn-icons-png.flaticon.com/512/1383/1383395.png";

	@Override
	protected void append(ILoggingEvent eventObject) {
		DiscordWebHook discordWebhook = new DiscordWebHook(username, avatarUrl, false);
		Map<String, String> mdcPropertyMap = eventObject.getMDCPropertyMap();
		Color messageColor = getLevelColor(eventObject);

		String level = eventObject.getLevel().levelStr;
		String exceptionBrief = "";
		String exceptionDetail = "";
		ThrowableProxy throwable = (ThrowableProxy)eventObject.getThrowableProxy();

		log.info("{}", eventObject.getMessage());
		if (throwable.getThrowable() == null) {
			exceptionBrief = "EXCEPTION 정보가 남지 않았습니다.";
		} else {
			if (throwable.getThrowable() instanceof ExpectedException expectedException) {
				exceptionBrief +=
					expectedException.getResponseCode().getErrorCode() + " : " + expectedException.getMessage();
			} else {
				exceptionBrief = throwable.getClass().getSimpleName() + " : " + throwable.getMessage();
			}
		}

		discordWebhook.addEmbed(EmbedObject.builder()
			.title("[" + level + " - 오류 간략 내용]")
			.color(messageColor)
			.description(exceptionBrief)
			.build()
			.addField(Field.builder()
				.name("[" + "Exception Level" + "]")
				.value(StringEscapeUtils.escapeJson(level))
				.inline(true)
				.build())
			.addField(Field.builder()
				.name("[오류 발생 시각]")
				.value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.inline(false)
				.build())
			.addField(Field.builder()
				.name("[" + MDCUtil.REQUEST_URI_MDC + "]")
				.value(StringEscapeUtils.escapeJson(mdcPropertyMap.get(MDCUtil.REQUEST_URI_MDC)))
				.inline(false)
				.build())
			.addField(Field.builder()
				.name("[" + MDCUtil.USER_IP_MDC + "]")
				.value(StringEscapeUtils.escapeJson(mdcPropertyMap.get(MDCUtil.USER_IP_MDC)))
				.inline(false)
				.build())
			.addField(Field.builder()
				.name("[" + MDCUtil.HEADER_MAP_MDC + "]")
				.value(StringEscapeUtils.escapeJson(
					mdcPropertyMap.get(MDCUtil.HEADER_MAP_MDC).replaceAll("[\\{\\{\\}]", "")))
				.inline(true)
				.build())
			.addField(Field.builder()
				.name("[" + MDCUtil.USER_REQUEST_COOKIES + "]")
				.value(StringEscapeUtils.escapeJson(
					mdcPropertyMap.get(MDCUtil.USER_REQUEST_COOKIES).replaceAll("[\\{\\{\\}]", "")))
				.inline(false)
				.build())
			.addField(Field.builder()
				.name("[" + MDCUtil.PARAMETER_MAP_MDC + "]")
				.value(StringEscapeUtils.escapeJson(
					mdcPropertyMap.get(MDCUtil.PARAMETER_MAP_MDC).replaceAll("[\\{\\{\\}]", "")))
				.inline(false)
				.build())
			.addField(Field.builder()
				.name("[" + MDCUtil.BODY_MDC + "]")
				.value(StringEscapeUtils.escapeJson(StringUtil.translateEscapes(mdcPropertyMap.get(MDCUtil.BODY_MDC))))
				.inline(false)
				.build()));

		exceptionDetail = ThrowableProxyUtil.asString(throwable);
		String exception = exceptionDetail.substring(0, 1000);
		discordWebhook.addEmbed(EmbedObject.builder()
			.title("[Exception 상세 내용]")
			.color(messageColor)
			.description(StringEscapeUtils.escapeJson(exception))
			.build());

		try {
			execute(discordWebhook, errorChannelUrl);
		} catch (IOException e) {
			throw ExpectedException.withLogging(ResponseCode.DiscordIOException, e.getMessage());
		}
	}

	public void signInAppend(Long totalUserCount, String name, String email, String socialPlatform,
		LocalDateTime createdAt) {
		if ("local".equals(profile)) {
			return;
		}
		DiscordWebHook discordWebhook = new DiscordWebHook("event", avatarUrl, false);

		discordWebhook.addEmbed(EmbedObject.builder()
			.title("[회원 가입] " + totalUserCount + "번째 유저가 가입하였습니다.")
			.color(Color.CYAN)
			.description("GenTI에 새로운 유저가 가입하였습니다.")
			.build()
			.addField(Field.builder().name("[이름]").value(name).inline(false).build())
			.addField(Field.builder().name("[이메일]").value(email).inline(false).build())
			.addField(Field.builder().name("[소셜 플랫폼]").value(socialPlatform).inline(false).build())
			.addField(Field.builder().name("[가입 일시]").value(String.valueOf(createdAt)).inline(false).build()));

		try {
			execute(discordWebhook, eventChannelUrl);
		} catch (IOException e) {
			throw ExpectedException.withLogging(ResponseCode.DiscordIOException, e.getMessage());
		}
	}

	public void matchResultAppend(String summary, List<String> matchingResultList) {
		if ("local".equals(profile)) {
			return;
		}
		DiscordWebHook discordWebhook = new DiscordWebHook("admin", avatarUrl, false);
		EmbedObject embedObject = EmbedObject.builder()
			.title("매칭 결과 알림")
			.color(Color.CYAN)
			.description(summary)
			.build();
		matchingResultList.forEach(
			str -> embedObject.addField(Field.builder().name("[매칭결과]").value(str).inline(false).build()));
		discordWebhook.addEmbed(embedObject);

		try {
			execute(discordWebhook, adminChannelUrl);
		} catch (IOException e) {
			throw ExpectedException.withLogging(ResponseCode.DiscordIOException, e.getMessage());
		}
	}

	public void execute(DiscordWebHook discordWebHook, String urlString) throws IOException {

		if (discordWebHook.getEmbeds().isEmpty()) {
			throw ExpectedException.withLogging(ResponseCode.NoWebhookEmbeds);
		}
		DiscordMessageSender.sendToDiscord(urlString, discordWebHook.createDiscordEmbedObject());
	}

	private static Color getLevelColor(ILoggingEvent eventObject) {
		String level = eventObject.getLevel().levelStr;
		if (level.equals("WARN")) {
			return Color.yellow;
		} else if (level.equals("ERROR")) {
			return Color.red;
		}

		return Color.blue;
	}

}
