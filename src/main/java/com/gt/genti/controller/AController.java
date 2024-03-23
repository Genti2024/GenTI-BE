package com.gt.genti.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gt.genti.service.AService;

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
