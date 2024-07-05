package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.PostStatus;

import jakarta.persistence.Converter;

@Converter

public class PostStatusConverter extends DefaultEnumDBConverter<PostStatus> {

	public PostStatusConverter() {
		super(PostStatus.class);
	}

}
