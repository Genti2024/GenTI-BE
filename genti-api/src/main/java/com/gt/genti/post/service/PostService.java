package com.gt.genti.post.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.gt.genti.post.model.Post;
import com.gt.genti.post.model.response.PostBriefFindResponseModel;
import com.gt.genti.post.repository.PostRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.post.dto.response.PostBriefFindResponseDto;
import com.gt.genti.post.dto.response.PostDetailResponseDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Deprecated
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {

	private static final int PAGE_SIZE = 10;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public List<PostDetailResponseDto> getPostDetailAllPagination(Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
		Slice<Post> postList;
		if (cursor == null || cursor <= 0) {
			postList = postRepository.findPostsTopByOrderByCreatedAtDesc(pageRequest);
		} else {
			postList = postRepository.findPostsNextCursorOrderByCreatedAtDesc(cursor, pageRequest);
		}
		return mapToPostResponseDto(postList);
	}

	public List<PostDetailResponseDto> getPostDetailAllByUserIdPagination(Long userId, Long cursor) {
		User foundUser = getUserByUserId(userId);
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
		Slice<Post> postList;
		if (cursor == null || cursor <= 0) {
			postList = postRepository.findPostsTopByUserOrderByCreatedAtDesc(foundUser, pageRequest);
		} else {
			postList = postRepository.findPostsNextCursorByUserIdOrderByCreatedAtDesc(foundUser, cursor, pageRequest);
		}
		return mapToPostResponseDto(postList);
	}

	private static List<PostDetailResponseDto> mapToPostResponseDto(Slice<Post> postList) {
		return postList.stream()
			.map(post -> PostDetailResponseDto.builder()
				.postId(post.getId())
				.picturePostList(post.getPictureList())
				.likes(post.getLikes())
				.userId(post.getUser().getId())
				.content(post.getContent())
				.pictureProfile(post.getUser().getPictureProfileList().get(0))
				.createdAt(post.getCreatedAt())
				.build()).toList();
	}

	public List<PostBriefFindResponseDto> getPostBriefAllByUserId(Long userId) {
		User foundUser = getUserByUserId(userId);
		List<PostBriefFindResponseModel> model = postRepository.findPostBriefByUser(foundUser);
		return model.stream().map(PostBriefFindResponseDto::of).toList();
	}

	public List<PostBriefFindResponseDto> getMyAllPostBrief(Long userId) {
		User foundUser = getUserByUserId(userId);
		List<PostBriefFindResponseModel> model = postRepository.findPostBriefByUser(foundUser);
		return model.stream().map(PostBriefFindResponseDto::of).toList();
	}

	private User getUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId.toString()));
	}

}
