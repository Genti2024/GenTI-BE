package com.gt.genti.picture.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.user.model.User;

@Deprecated
public interface PictureProfileRepository extends JpaRepository<PictureProfile, Long> {
	Optional<PictureProfile> findByKey(String key);

	List<PictureProfile> findAllByUserOrderByCreatedAtDesc(User foundUser);

}
