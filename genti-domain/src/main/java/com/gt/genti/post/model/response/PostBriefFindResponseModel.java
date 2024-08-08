package com.gt.genti.post.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostBriefFindResponseModel {
	@Schema(name = "postId")
	Long postId;
	@Schema(name = "mainPictureUrl")
	String mainPictureUrl;

	@Builder
	public PostBriefFindResponseModel(Long postId, String mainPictureUrl) {
		this.postId = postId;
		this.mainPictureUrl = mainPictureUrl;
	}


}

