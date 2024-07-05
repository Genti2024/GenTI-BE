package com.gt.genti.dto.user.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PicturePost;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.other.util.PictureEntityUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
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
		this.profilePicture = PictureEntityUtils.toCommonResponse(pictureProfile);
		this.postPictureList = picturePostList.stream().map(PictureEntityUtils::toCommonResponse).toList();
		this.content = content;
		this.likes = likes;
		this.createdAt = createdAt;
	}
}
