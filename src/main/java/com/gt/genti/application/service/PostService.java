package com.gt.genti.application.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.gt.genti.domain.Post;
import com.gt.genti.dto.PostBriefResponseDto;
import com.gt.genti.dto.PostDetailResponseDto;
import com.gt.genti.repository.PostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {

	private static final int PAGE_SIZE = 10;
	private final PostRepository postRepository;

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
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
		Slice<Post> postList;
		if (cursor == null || cursor <= 0) {
			postList = postRepository.findPostsTopByUserIdOrderByCreatedAtDesc(userId, pageRequest);
		} else {
			postList = postRepository.findPostsNextCursorByUserIdOrderByCreatedAtDesc(userId, cursor, pageRequest);
		}
		return mapToPostResponseDto(postList);
	}

	private static List<PostDetailResponseDto> mapToPostResponseDto(Slice<Post> postList) {
		return postList.stream()
			.map(post -> PostDetailResponseDto.builder()
				.postId(post.getId())
				.postImageUrl(
					post.getPictureList().stream().map(postPicture -> postPicture.getPicture().getUrl()).toList())
				.likes(post.getLikes())
				.userId(post.getUser().getId())
				.content(post.getContent())
				.profileImageUrl(post.getUser().getProfilePicture().getPicture().getUrl())
				.createdAt(post.getCreatedAt())
				.build()).toList();
	}

	public List<PostBriefResponseDto> getPostBriefAllByUserId(Long userId) {
		return postRepository.findPostBriefByUserId(userId);
	}
}
