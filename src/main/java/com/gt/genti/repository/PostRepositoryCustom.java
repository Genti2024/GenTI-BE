package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gt.genti.dto.PostResponseDto;

@Repository
public interface PostRepositoryCustom {

	List<PostResponseDto> findByIdBetween(@Param("startId") Long startId, @Param("endId") Long endId);
}
