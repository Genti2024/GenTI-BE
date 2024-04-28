package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.OauthType;

public class OauthTypeConverterIgnoreCase extends IgnoreCaseStringAttributeConverter<OauthType> {

	public OauthTypeConverterIgnoreCase() {
		super(OauthType.class);
	}
}