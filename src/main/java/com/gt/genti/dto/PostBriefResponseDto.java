package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostBriefResponseDto {
	private Long postId;
	private String mainPictureUrl;

	@Builder
	public PostBriefResponseDto(Long postId, String mainPictureUrl) {
		this.postId = postId;
		this.mainPictureUrl = mainPictureUrl;
	}
}
