package com.gt.genti.application.service;

import static com.gt.genti.error.ResponseCode.*;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.gt.genti.domain.Post;
import com.gt.genti.domain.User;
import com.gt.genti.dto.user.response.PostBriefFindResponseDto;
import com.gt.genti.dto.user.response.PostDetailResponseDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.PostRepository;
import com.gt.genti.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

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

	public List<PostDetailResponseDto> getPostDetailAllByUserPagination(User user, Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
		Slice<Post> postList;
		if (cursor == null || cursor <= 0) {
			postList = postRepository.findPostsTopByUserOrderByCreatedAtDesc(user, pageRequest);
		} else {
			postList = postRepository.findPostsNextCursorByUserIdOrderByCreatedAtDesc(user, cursor, pageRequest);
		}
		return mapToPostResponseDto(postList);
	}

	public List<PostDetailResponseDto> getPostDetailAllByUserIdPagination(Long userId, Long cursor) {
		User foundUser = findUser(userId);
		return getPostDetailAllByUserPagination(foundUser, cursor);
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
		User foundUser = findUser(userId);
		return postRepository.findPostBriefByUser(foundUser);
	}

	public List<PostBriefFindResponseDto> getPostBriefAllByUser(User user) {
		return postRepository.findPostBriefByUser(user);
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(UserNotFound, userId.toString()));
	}

}
