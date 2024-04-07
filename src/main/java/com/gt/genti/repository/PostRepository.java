package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.Post;
import com.gt.genti.dto.PostResponseDto;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select new "
		+ "com.gt.genti.dto.PostResponseDto("
		+ "p.id, u.id, "
		+ "(select pic.url from Picture pic where pic.id=pi.picture.id) ,"
		+ "(select pic.url from Picture pic inner join PostPicture pp where pic.id = pp.picture.id), "
		+ "p.content, p.likes, p.createdAt) "
		+ "from Post p inner join p.user u inner join u.profilePicture pi "
		+ "where p.user.id = u.id and u.profilePicture.id = pi.id and "
		+ "p.id between :startId and :endId and "
		+ "p.postStatus != 'DELETED' "
		+ "order by p.id desc")
	List<PostResponseDto> findByIdBetween(Long startId, Long endId);
}
