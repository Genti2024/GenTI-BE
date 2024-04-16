package com.gt.genti.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
	Long postId;
	Long userId;
	String profileImageUrl;
	List<String> postImageUrl;
	String content;
	Integer likes;
	LocalDateTime createdAt;

	public PostResponseDto(Long postId, Long userId, String profileImageUrl, List<String> postImageUrl, String content,
		Integer likes, LocalDateTime createdAt) {
		this.postId = postId;
		this.userId = userId;
		this.profileImageUrl = profileImageUrl;
		this.postImageUrl = postImageUrl;
		this.content = content;
		this.likes = likes;
		this.createdAt = createdAt;
	}
}
