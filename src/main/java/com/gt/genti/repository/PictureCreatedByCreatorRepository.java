package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PictureGenerateResponse;

public interface PictureCreatedByCreatorRepository extends JpaRepository<PictureCreatedByCreator, Long> {
	Optional<PictureCreatedByCreator> findByKey(String key);

	Optional<PictureCreatedByCreator> findByPictureGenerateResponse(PictureGenerateResponse pictureGenerateResponse);
}
