package com.gt.genti.adapter.in.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gt.genti.domain.User;
import com.gt.genti.application.service.SSEService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sse")
public class SSEController {

	private final SSEService sseService;

	@GetMapping("")
	public SseEmitter subscribe(@AuthenticationPrincipal User user) {
		return sseService.subscribe(user.getId());
	}

	@GetMapping("/{id}")
	public SseEmitter testSubscribe(@PathVariable Long id) {
		return sseService.subscribe(id);
	}

	@PostMapping("/test/{id}")
	public ResponseEntity<Void> test(@PathVariable Long id) {
		sseService.notify(id, Map.of("테스트키1", "테스트밸류1", "테스트키2", "테스트밸류2"));
		return ResponseEntity.ok().build();
	}
}