package com.gt.genti.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PicturePost;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.common.PictureEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
	Long postId;
	Long userId;
	CommonPictureResponseDto profilePicture;
	List<CommonPictureResponseDto> postPictureList;
	String content;
	Integer likes;
	LocalDateTime createdAt;

	@Builder
	public PostDetailResponseDto(Long postId, Long userId, PictureProfile pictureProfile,
		List<PicturePost> picturePostList, String content,
		Integer likes, LocalDateTime createdAt) {
		this.postId = postId;
		this.userId = userId;
		this.profilePicture = pictureProfile.mapToCommonResponse();
		this.postPictureList = picturePostList.stream().map(PictureEntity::mapToCommonResponse).toList();
		this.content = content;
		this.likes = likes;
		this.createdAt = createdAt;
	}
}
