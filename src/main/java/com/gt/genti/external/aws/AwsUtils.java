package com.gt.genti.external.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsUtils {

	@Value("${aws.cloudfront.url}")
	private String cloudfrontUrl;

	public String getCloudfrontUrl(String key) {
		return cloudfrontUrl + "/" + key;
	}
}
