package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureCreatedByCreator;

public interface PictureCompletedRepository extends JpaRepository<PictureCompleted, Long> {
	Optional<PictureCompleted> findByUrl(String url);

}
