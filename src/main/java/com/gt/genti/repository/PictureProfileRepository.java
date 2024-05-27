package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;

public interface PictureProfileRepository extends JpaRepository<PictureProfile, Long> {
	Optional<PictureProfile> findByUrl(String url);

	List<PictureProfile> findAllByUserOrderByCreatedAtDesc(User foundUser);

}
