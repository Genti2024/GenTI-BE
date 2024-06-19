package com.gt.genti.dto.user.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PicturePost;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.other.util.PictureEntityUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Deprecated
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
		this.postPictureList = picturePostList.stream().map(PictureEntityUtils::toCommonResponse).toList();
		this.content = content;
		this.likes = likes;
		this.createdAt = createdAt;
	}
}
