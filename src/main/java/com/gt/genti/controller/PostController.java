package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.aop.CheckUserIsQuit;
import com.gt.genti.dto.PostResponseDto;
import com.gt.genti.security.PrincipalDetail;
import com.gt.genti.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@CheckUserIsQuit
	@GetMapping("")
	public ResponseEntity<ApiResult<List<PostResponseDto>>> getPosts(
		@AuthenticationPrincipal PrincipalDetail principalDetail,
		@RequestParam(value = "cursor", required = false) Long cursor,
		@RequestParam(value = "limit", defaultValue = "10") int limit) {
		System.out.println("왜?");
		return success(postService.getPosts(principalDetail.getUser(), cursor, limit));
	}

}
