package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.Post;
import com.gt.genti.dto.PostResponseDto;

import lombok.RequiredArgsConstructor;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
