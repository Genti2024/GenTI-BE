package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PictureProfile;

public interface PictureProfileRepository extends JpaRepository<PictureProfile, Long> {
	Optional<PictureProfile> findByUrl(String url);

}
