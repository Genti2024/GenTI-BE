package com.gt.genti.test;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gt.genti.picturegenerateresponse.service.PGRESCompleteEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/v1/fcmtest")
public class FcmTestController {
	private final PGRESCompleteEventPublisher PGRESCompleteEventPublisher;

	@GetMapping("/{receiverId}")
	public ResponseEntity<Object> t1(@PathVariable Long receiverId) {
		PGRESCompleteEventPublisher.publishPictureGenerateCompleteEvent(receiverId);
		return ResponseEntity.ok().build();
	}
}
