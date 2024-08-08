package com.gt.genti.picture.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.picture.post.model.PicturePost;

@Deprecated
@Repository
public interface PicturePostRepository extends JpaRepository<PicturePost, Long> {
	Optional<PicturePost> findByKey(String key);

}
