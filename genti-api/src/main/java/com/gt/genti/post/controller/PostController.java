package com.gt.genti.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.post.dto.response.PostBriefFindResponseDto;
import com.gt.genti.post.dto.response.PostDetailResponseDto;
import com.gt.genti.post.service.PostService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[PostController] 포스트 컨트롤러", description = "포스트(피드) 조회,생성,수정 요청")
@Deprecated
@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@Deprecated
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/detail")
	public ResponseEntity<ApiResult<List<PostDetailResponseDto>>> getAllPostsDetailPagination(
		@RequestParam(value = "cursor", required = false) Long cursor) {
		return GentiResponse.success(postService.getPostDetailAllPagination(cursor));
	}

	@Deprecated
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/detail/my")
	public ResponseEntity<ApiResult<List<PostDetailResponseDto>>> getMyAllPostsDetailPagination(
		@AuthUser Long userId,
		@RequestParam(value = "cursor", required = false) Long cursor) {
		return GentiResponse.success(postService.getPostDetailAllByUserIdPagination(userId, cursor));
	}

	@Deprecated
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/detail/users/{userId}")
	public ResponseEntity<ApiResult<List<PostDetailResponseDto>>> getUsersAllPostsDetailPagination(
		@PathVariable(value = "userId") Long userId,
		@RequestParam(value = "cursor", required = false) Long cursor) {
		return GentiResponse.success(postService.getPostDetailAllByUserIdPagination(userId, cursor));
	}

	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Deprecated
	@GetMapping("/brief/users/{userId}")
	public ResponseEntity<ApiResult<List<PostBriefFindResponseDto>>> getUsersAllPostBrief(
		@PathVariable(value = "userId") Long userId) {
		return GentiResponse.success(postService.getPostBriefAllByUserId(userId));
	}

	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Deprecated
	@GetMapping("/brief/my")
	public ResponseEntity<ApiResult<List<PostBriefFindResponseDto>>> getMyAllPostBrief(
		@AuthUser Long userId) {
		return GentiResponse.success(postService.getMyAllPostBrief(userId));
	}

}
