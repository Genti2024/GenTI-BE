package com.gt.genti.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.picture.post.model.PicturePost;
import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(deprecated = true)
@Deprecated
@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
	@Schema(name = "postId")
	Long postId;
	@Schema(name = "userId")
	Long userId;
	@Schema(name = "profilePicture")
	CommonPictureResponseDto profilePicture;
	@Schema(name = "postPictureList")
	List<CommonPictureResponseDto> postPictureList;
	@Schema(name = "content")
	String content;
	@Schema(name = "likes")
	Integer likes;
	@Schema(name = "createdAt")
	LocalDateTime createdAt;

	@Builder
	public PostDetailResponseDto(Long postId, Long userId, PictureProfile pictureProfile,
		List<PicturePost> picturePostList, String content,
		Integer likes, LocalDateTime createdAt) {
		this.postId = postId;
		this.userId = userId;
		this.profilePicture = CommonPictureResponseDto.of(pictureProfile);
		this.postPictureList = picturePostList.stream().map(CommonPictureResponseDto::of).toList();
		this.content = content;
		this.likes = likes;
		this.createdAt = createdAt;
	}
}
