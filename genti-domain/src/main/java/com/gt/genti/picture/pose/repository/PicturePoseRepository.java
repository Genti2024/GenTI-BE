package com.gt.genti.picture.pose.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.picture.pose.model.PicturePose;

@Repository
public interface PicturePoseRepository extends JpaRepository<PicturePose, Long> {
	Optional<PicturePose> findByKey(String key);

}
