package com.gt.genti.common.converter;

import com.gt.genti.user.model.OauthPlatform;

public class OauthPlatformConverterIgnoreCase extends IgnoreCaseEnumDBConverter<OauthPlatform> {

	public OauthPlatformConverterIgnoreCase() {
		super(OauthPlatform.class);
	}
}