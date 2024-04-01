package com.gt.genti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.repository.PostRepository;
import com.gt.genti.dto.PostResponseDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {

	private final PostRepository postRepository;

	public List<PostResponseDto> getPosts(Long cursor, int limit) {
		if (cursor == null) {
			cursor = 0L;
		}

		return postRepository.findByIdBetween(cursor, cursor + limit);
	}
}
