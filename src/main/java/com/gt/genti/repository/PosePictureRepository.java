package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PosePicture;

public interface PosePictureRepository extends JpaRepository<PosePicture, Long> {
	Optional<PosePicture> findByUrl(String url);
}
