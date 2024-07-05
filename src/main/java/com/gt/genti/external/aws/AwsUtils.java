package com.gt.genti.external.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsUtils {

	public static String CLOUDFRONT_BASEURL;

	@Value("${aws.cloudfront.url}")
	public void setCloudFrontUrl(String cloudFrontUrl) {
		AwsUtils.CLOUDFRONT_BASEURL = cloudFrontUrl;
	}

	public String getCloudfrontUrl() {
		return CLOUDFRONT_BASEURL;
	}
}
