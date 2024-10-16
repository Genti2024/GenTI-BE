package com.gt.genti.picture.createdbycreator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;

public interface PictureCreatedByCreatorRepository extends JpaRepository<PictureCreatedByCreator, Long> {
	Optional<PictureCreatedByCreator> findByKey(String key);

	Optional<PictureCreatedByCreator> findByPictureGenerateResponse(PictureGenerateResponse pictureGenerateResponse);
}
