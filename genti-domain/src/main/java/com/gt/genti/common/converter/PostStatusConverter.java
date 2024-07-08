package com.gt.genti.common.converter;

import com.gt.genti.post.model.PostStatus;

import jakarta.persistence.Converter;

@Converter

public class PostStatusConverter extends DefaultEnumDBConverter<PostStatus> {

	public PostStatusConverter() {
		super(PostStatus.class);
	}

}
