package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.PostStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter

public class PostStatusConverter implements AttributeConverter<PostStatus, String> {

	@Override
	public String convertToDatabaseColumn(PostStatus enumValue) {
		return enumValue.getStatus();
	}

	@Override
	public PostStatus convertToEntityAttribute(String value) {
		for (PostStatus postStatus : PostStatus.values()) {
			if (postStatus.getStatus().equals(value)) {
				return postStatus;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
	}
}
