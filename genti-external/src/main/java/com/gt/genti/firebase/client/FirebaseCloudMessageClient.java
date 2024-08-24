package com.gt.genti.firebase.client;

import static com.gt.genti.firebase.common.NotificationType.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.google.auth.oauth2.GoogleCredentials;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.fcm.model.FcmToken;
import com.gt.genti.fcm.repository.FcmTokenRepository;
import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.common.NotificationType;
import com.gt.genti.firebase.event.NotificationEvent;
import com.gt.genti.firebase.generator.NotificationMessageGenerator;
import com.gt.genti.firebase.generator.PictureGenerationCompletedMessageGenerator;
import com.gt.genti.firebase.generator.PictureGenerationFailedMessageGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageClient {

	private static final String PREFIX_ACCESS_TOKEN = "Bearer ";
	private static final String PREFIX_FCM_REQUEST_URL = "https://fcm.googleapis.com:443/v1/projects/";
	private static final String POSTFIX_FCM_REQUEST_URL = "/messages:send";
	private static final String FIREBASE_KEY_PATH = "/firebase-genti.json";
	private static final String GOOGLE_AUTH_URL = "https://www.googleapis.com/auth/cloud-platform";
	private static final Map<NotificationType, Function<NotificationEvent, NotificationMessageGenerator>> GENERATOR_MAP =
		Map.of(
			PICTURE_GENERATION_COMPLETED, PictureGenerationCompletedMessageGenerator::new,
			PICTURE_GENERATION_FAILED, PictureGenerationFailedMessageGenerator::new
		);

	private final RestClient restClient = RestClient.create();
	private final FcmTokenRepository fcmTokenRepository;

	@Value("${firebase.project_id}")
	private String projectId;

	public void sendMessageTo(final NotificationEvent notificationEvent) {
		sendMessageTo(
			notificationEvent.getReceiverId(),
			GENERATOR_MAP.get(notificationEvent.getNotificationType()).apply(notificationEvent)
		);
	}

	private void sendMessageTo(
		final Long receiverId,
		final NotificationMessageGenerator messageGenerator
	) {
		final FcmToken fcmToken = fcmTokenRepository.findByUserId(receiverId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.FCM_TOKEN_NOT_FOUND, receiverId));
		log.info("receiverId : {} 에게 전달할 수 있는 FCMToken 식별 완료", receiverId);
		final Notification testNotification = new Notification("title", "body");
		final String message = messageGenerator.makeMessage(
			fcmToken.getToken(), testNotification
		);

		final String fcmRequestUrl = PREFIX_FCM_REQUEST_URL + projectId + POSTFIX_FCM_REQUEST_URL;

		restClient.post()
			.uri(fcmRequestUrl)
			.contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
			.header("Authorization", PREFIX_ACCESS_TOKEN + getAccessToken())
			.body(message)
			.retrieve();
		log.info("푸시알림 전송 완료");
		// .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
		// 	throw new RuntimeException(res);
		// }).onStatus()
	}

	private String getAccessToken() {
		try {
			final GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(new ClassPathResource(FIREBASE_KEY_PATH).getInputStream())
				.createScoped(List.of(GOOGLE_AUTH_URL));

			googleCredentials.refreshIfExpired();

			return googleCredentials.getAccessToken().getTokenValue();
		} catch (IOException e) {
			log.info("Firebase key로 AccessToken 요청 오류");
			log.error(e.getLocalizedMessage(), e);
			throw new RuntimeException(e);
		}
	}
}