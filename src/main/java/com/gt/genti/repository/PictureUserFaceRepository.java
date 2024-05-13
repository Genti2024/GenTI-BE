package com.gt.genti.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureUserFace;

public interface PictureUserFaceRepository extends JpaRepository<PictureUserFace, Long> {
	Optional<PictureUserFace> findByUrl(String url);

	List<PictureUserFace> findAllByUrlIsIn(Collection<String> url);
}
