package com.gt.genti.test;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class FcmTestController {
	private final FcmEventPublisher fcmEventPublisher;
	@GetMapping("/t1")
	public ResponseEntity<Object> t1(){
		fcmEventPublisher.publishTest1();
		return ResponseEntity.ok().build();
	}
}
