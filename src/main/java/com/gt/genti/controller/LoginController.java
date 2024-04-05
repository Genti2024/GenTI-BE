package com.gt.genti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gt.genti.config.auth.SessionUserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@GetMapping("/")
	public String index(Model model) {
		log.info("인덱스 컨트롤러 접근");
		SessionUserDto user = (SessionUserDto)model.getAttribute("user");
		if (user != null) {
			model.addAttribute("userName", user.getName());
		}
		return "index";
	}

}
