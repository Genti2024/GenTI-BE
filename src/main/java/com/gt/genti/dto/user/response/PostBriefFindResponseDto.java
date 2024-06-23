package com.gt.genti.dto.user.response;

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
}
