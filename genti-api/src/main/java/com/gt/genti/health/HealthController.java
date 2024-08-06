package com.gt.genti.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthController {

	@GetMapping("/health")
	public ResponseEntity<?> health() {
		return ResponseEntity.ok().build();
	}
}
