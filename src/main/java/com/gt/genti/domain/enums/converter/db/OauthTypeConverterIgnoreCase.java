package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.OauthType;

public class OauthTypeConverterIgnoreCase extends IgnoreCaseEnumDBConverter<OauthType> {

	public OauthTypeConverterIgnoreCase() {
		super(OauthType.class);
	}
}