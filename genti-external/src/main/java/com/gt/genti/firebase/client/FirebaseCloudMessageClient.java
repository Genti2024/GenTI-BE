package com.gt.genti.firebase.client;

import static com.gt.genti.firebase.common.NotificationType.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.fcm.model.FcmToken;
import com.gt.genti.fcm.repository.FcmTokenRepository;
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
	private static final Map<NotificationType, Function<NotificationEvent, NotificationMessageGenerator>> GENERATOR_MAP = Map.of(
		PICTURE_GENERATION_COMPLETED, PictureGenerationCompletedMessageGenerator::new, PICTURE_GENERATION_FAILED,
		PictureGenerationFailedMessageGenerator::new);

	private final RestClient restClient = RestClient.create();
	private final FcmTokenRepository fcmTokenRepository;

	@Value("${firebase.type}")
	private String type;
	@Value("${firebase.project_id}")
	private String projectId;
	@Value("${firebase.private_key_id}")
	private String privateKeyId;
	@Value("${firebase.private_key}")
	private String privateKey;
	@Value("${firebase.client_email}")
	private String clientEmail;
	@Value("${firebase.client_id}")
	private String clientId;
	@Value("${firebase.auth_uri}")
	private String authUri;
	@Value("${firebase.token_uri}")
	private String tokenUri;
	@Value("${firebase.auth_provider_x509_cert_url}")
	private String authProviderX509CertUrl;
	@Value("${firebase.client_x509_cert_url}")
	private String clientX509CertUrl;
	@Value("${firebase.universe_domain}")
	private String universeDomain;

	public void sendMessage(final NotificationEvent notificationEvent) {
		sendMessage(notificationEvent,
			GENERATOR_MAP.get(notificationEvent.getNotificationType()).apply(notificationEvent));
	}

	private void sendMessage(final NotificationEvent notificationEvent,
		final NotificationMessageGenerator messageGenerator) {
		Long receiverId = notificationEvent.getReceiverId();
		final FcmToken fcmToken = fcmTokenRepository.findByUserId(receiverId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.FCM_TOKEN_NOT_FOUND, receiverId));
		log.info("receiverId : {} 에게 전달할 수 있는 FCMToken 식별 완료", receiverId);

		final String message = messageGenerator.makeMessage(fcmToken.getToken());
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
			URI tokenServerUriFromCreds = new URI(tokenUri);
			final GoogleCredentials googleCredentials = ServiceAccountCredentials.fromPkcs8(clientId, clientEmail,
				privateKey, privateKeyId, List.of(GOOGLE_AUTH_URL),
				new HttpTransportOptions.DefaultHttpTransportFactory(), tokenServerUriFromCreds);

			googleCredentials.refreshIfExpired();

			return googleCredentials.getAccessToken().getTokenValue();
		} catch (IOException e) {
			log.info("Firebase key로 AccessToken 요청 오류");
			log.error(e.getLocalizedMessage(), e);
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}