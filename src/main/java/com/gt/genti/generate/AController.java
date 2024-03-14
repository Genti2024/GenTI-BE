package com.gt.genti.generate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("api/a")
@RequiredArgsConstructor
public class AController {

	private final AService aService;

	@GetMapping()
	ResponseEntity<?> getRequest(Long id){
		return ResponseEntity.ok(aService.getRequest(id));
	}
}
