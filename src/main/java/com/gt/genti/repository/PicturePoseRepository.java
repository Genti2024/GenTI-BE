package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PicturePose;

@Repository
public interface PicturePoseRepository extends JpaRepository<PicturePose, Long> {
	Optional<PicturePose> findByUrl(String url);

}
