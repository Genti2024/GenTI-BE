package com.gt.genti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.User;
import com.gt.genti.dto.PostResponseDto;
import com.gt.genti.repository.PostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {

	private final PostRepository postRepository;

	public List<PostResponseDto> getPosts(User user, Long cursor, int limit) {
		if (cursor == null) {
			cursor = 0L;
		}
		List<PostResponseDto> d = postRepository.findByIdBetween(cursor, cursor + limit);
		return null;
	}
}
