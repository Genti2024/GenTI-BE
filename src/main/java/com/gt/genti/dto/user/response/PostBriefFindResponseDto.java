package com.gt.genti.dto.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Deprecated
@Getter
@NoArgsConstructor
public class PostBriefFindResponseDto {
	private Long postId;
	private String mainPictureUrl;

	@Builder
	public PostBriefFindResponseDto(Long postId, String mainPictureUrl) {
		this.postId = postId;
		this.mainPictureUrl = mainPictureUrl;
	}
}
