package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.PostResponseDto;
import com.gt.genti.service.PostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostController {
	private final PostService postService;

	@GetMapping("/")
	public ResponseEntity<ApiResult<List<PostResponseDto>>> getPosts(
		@RequestParam(value = "cursor", required = false) Long cursor,
		@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return success(postService.getPosts(cursor, limit));
	}

}
