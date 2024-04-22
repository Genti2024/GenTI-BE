package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.Post;
import com.gt.genti.dto.PostBriefResponseDto;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p where p.postStatus != 'DELETED' order by p.createdAt desc")
	Slice<Post> findPostsTopByOrderByCreatedAtDesc(PageRequest pageRequest);

	@Query("select p from Post p where p.postStatus != 'DELETED' and p.id < :cursor order by p.createdAt desc")
	Slice<Post> findPostsNextCursorOrderByCreatedAtDesc(@Param("cursor") Long cursor, PageRequest pageRequest);

	@Query("select p "
		+ "from Post p "
		+ "join p.user u "
		+ "where p.user.id = u.id and "
		+ "u.id = :userId and "
		+ "p.postStatus != 'DELETED' "
		+ "order by p.createdAt desc ")
	Slice<Post> findPostsTopByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, PageRequest pageRequest);

	@Query("select p "
		+ "from Post p "
		+ "join p.user u "
		+ "where p.user.id = u.id and "
		+ "u.id = :userId and "
		+ "p.id < :cursor and "
		+ "p.postStatus != 'DELETED' "
		+ "order by p.createdAt desc ")
	Slice<Post> findPostsNextCursorByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId,
		@Param("cursor") Long cursor, PageRequest pageRequest);

	@Query("select new com.gt.genti.dto.PostBriefResponseDto( "
		+ "p.id,"
		+ "pic.url)"
		+ "from Post p "
		+ "join p.mainPicture pic "
		+ "join p.user u "
		+ "where p.mainPicture.id = pic.id and "
		+ "p.user.id = :userId "
		+ "order by p.createdAt desc ")
	List<PostBriefResponseDto> findPostBriefByUserId(@Param("userId") Long userId);
}
