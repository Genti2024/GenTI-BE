package com.gt.genti.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureUserFace;

public interface PictureUserFaceRepository extends JpaRepository<PictureUserFace, Long> {
	List<PictureUserFace> findAllByUrlIsIn(Collection<String> url);
}
