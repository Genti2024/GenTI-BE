package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PostService;
import com.gt.genti.dto.user.response.PostBriefFindResponseDto;
import com.gt.genti.dto.user.response.PostDetailResponseDto;
import com.gt.genti.other.annotation.ToBeUpdated;
import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@ToBeUpdated
	@CheckUserIsQuit
	@GetMapping("/detail")
	public ResponseEntity<ApiResult<List<PostDetailResponseDto>>> getAllPostsDetailPagination(
		@RequestParam(value = "cursor", required = false) Long cursor) {

		return success(postService.getPostDetailAllPagination(cursor));
	}

	@ToBeUpdated
	@CheckUserIsQuit
	@GetMapping("/detail/my")
	public ResponseEntity<ApiResult<List<PostDetailResponseDto>>> getMyAllPostsDetailPagination(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestParam(value = "cursor", required = false) Long cursor) {
		return success(postService.getPostDetailAllByUserIdPagination(userDetails.getId(), cursor));
	}

	@ToBeUpdated
	@CheckUserIsQuit
	@GetMapping("/detail/users/{userId}")
	public ResponseEntity<ApiResult<List<PostDetailResponseDto>>> getUsersAllPostsDetailPagination(
		@PathVariable(name = "userId") Long userId,
		@RequestParam(value = "cursor", required = false) Long cursor) {
		return success(postService.getPostDetailAllByUserIdPagination(userId, cursor));
	}

	@ToBeUpdated
	@CheckUserIsQuit
	@GetMapping("/brief/users/{userId}")
	public ResponseEntity<ApiResult<List<PostBriefFindResponseDto>>> getUsersAllPostBrief(
		@PathVariable(name = "userId") Long userId) {
		return success(postService.getPostBriefAllByUserId(userId));
	}

	@ToBeUpdated
	@CheckUserIsQuit
	@GetMapping("/brief/my")
	public ResponseEntity<ApiResult<List<PostBriefFindResponseDto>>> getUsersAllPostBrief(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(postService.getPostBriefAllByUserId(userDetails.getId()));
	}

}
