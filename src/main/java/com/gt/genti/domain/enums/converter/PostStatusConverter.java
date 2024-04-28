package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.PostStatus;

import jakarta.persistence.Converter;

@Converter

public class PostStatusConverter extends DefaultStringAttributeConverter<PostStatus> {

	public PostStatusConverter() {
		super(PostStatus.class);
	}

}
