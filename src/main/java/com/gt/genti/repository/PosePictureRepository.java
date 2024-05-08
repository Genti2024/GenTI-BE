package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PicturePose;

public interface PosePictureRepository extends JpaRepository<PicturePose, Long> {
	Optional<PicturePose> findByUrl(String url);
}
