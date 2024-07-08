package com.gt.genti.post.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gt.genti.post.model.Post;
import com.gt.genti.post.model.response.PostBriefFindResponseModel;
import com.gt.genti.user.model.User;

@Deprecated
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("select p from Post p where p.postStatus != 'DELETED' order by p.createdAt desc")
	Slice<Post> findPostsTopByOrderByCreatedAtDesc(PageRequest pageRequest);

	@Query("select p from Post p where p.postStatus != 'DELETED' and p.id < :cursor order by p.createdAt desc")
	Slice<Post> findPostsNextCursorOrderByCreatedAtDesc(@Param("cursor") Long cursor, PageRequest pageRequest);

	@Query("select p "
		+ "from Post p "
		+ "join p.user u "
		+ "where p.user =:user and "
		+ "p.postStatus != 'DELETED' "
		+ "order by p.createdAt desc ")
	Slice<Post> findPostsTopByUserOrderByCreatedAtDesc(@Param("user") User user, PageRequest pageRequest);

	@Query("select p "
		+ "from Post p "
		+ "join p.user u "
		+ "where p.user = u and "
		+ "u = :user and "
		+ "p.id < :cursor and "
		+ "p.postStatus != 'DELETED' "
		+ "order by p.createdAt desc ")
	Slice<Post> findPostsNextCursorByUserIdOrderByCreatedAtDesc(@Param("user") User user,
		@Param("cursor") Long cursor, PageRequest pageRequest);

	@Query("select new com.gt.genti.post.model.response.PostBriefFindResponseModel( "
		+ "p.id,"
		+ "pic.key)"
		+ "from Post p "
		+ "join p.mainPicture pic "
		+ "join p.user u "
		+ "where p.mainPicture.id = pic.id and "
		+ "p.user = u and "
		+ "p.user = :user "
		+ "order by p.createdAt desc ")
	List<PostBriefFindResponseModel> findPostBriefByUser(@Param("user") User user);


}
