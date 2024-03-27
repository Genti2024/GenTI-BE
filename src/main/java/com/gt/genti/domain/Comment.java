// package com.gt.genti.domain;
//
// import com.gt.genti.domain.common.BaseTimeEntity;
//
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToOne;
// import lombok.AccessLevel;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
//
// @Entity
// @Getter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// public class Comment extends BaseTimeEntity {
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	Long id;
//
// 	@ManyToOne
// 	@JoinColumn(name = "post_id")
// 	Post post;
//
// 	@ManyToOne
// 	@JoinColumn(name = "user_id")
// 	User user;
//
// 	@OneToOne
// 	@JoinColumn(name = "recur_comment_id")
// 	Comment comment;
//
// 	String content;
//
// 	@Builder
// 	public Comment(Long id, Post post, User user, Comment comment, String content) {
// 		this.id = id;
// 		this.post = post;
// 		this.user = user;
// 		this.comment = comment;
// 		this.content = content;
// 	}
// }
