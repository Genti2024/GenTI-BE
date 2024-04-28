package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.OauthType;

public class OauthTypeConverter extends DefaultStringAttributeConverter<OauthType> {

	public OauthTypeConverter() {
		super(OauthType.class);
	}
}