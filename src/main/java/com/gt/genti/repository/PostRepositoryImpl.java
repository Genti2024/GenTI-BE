package com.gt.genti.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gt.genti.domain.Post;
import com.gt.genti.dto.PostResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {


	@Override
	public List<PostResponseDto> findByIdBetween(Long startId, Long endId) {
		return null;
	}
}
