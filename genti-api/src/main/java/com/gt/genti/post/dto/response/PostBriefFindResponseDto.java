package com.gt.genti.post.dto.response;

import com.gt.genti.post.model.response.PostBriefFindResponseModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Deprecated
@Getter
@NoArgsConstructor
public class PostBriefFindResponseDto {
	@Schema(name = "postId")
	Long postId;
	@Schema(name = "mainPictureUrl")
	String mainPictureUrl;

	@Builder
	public PostBriefFindResponseDto(Long postId, String mainPictureUrl) {
		this.postId = postId;
		this.mainPictureUrl = mainPictureUrl;
	}
	public static PostBriefFindResponseDto of(PostBriefFindResponseModel model) {
		return PostBriefFindResponseDto.builder()
			.postId(model.getPostId())
			.mainPictureUrl(model.getMainPictureUrl())
			.build();
	}
}
