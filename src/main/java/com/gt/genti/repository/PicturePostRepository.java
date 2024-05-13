package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PicturePost;

public interface PicturePostRepository extends JpaRepository<PicturePost, Long> {
	Optional<PicturePost> findByUrl(String url);

}
