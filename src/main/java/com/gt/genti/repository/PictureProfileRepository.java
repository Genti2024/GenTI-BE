package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;

public interface PictureProfileRepository extends JpaRepository<PictureProfile, Long> {
	Optional<PictureProfile> findByKey(String key);

	List<PictureProfile> findAllByUserOrderByCreatedAtDesc(User foundUser);

}
