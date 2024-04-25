// package com.gt.genti.external.aws.service;
//
// import java.util.Date;
// import java.util.UUID;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpMethod;
// import org.springframework.stereotype.Service;
//
// import com.amazonaws.services.s3.Headers;
// import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
// import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Service
// @Slf4j
// public class S3Service {
//
// 	@Value("${aws.s3.bucket}")
// 	private String bucket;
//
// 	@Value("${aws.region.static}")
// 	private String region;
// 	public String getPreSignedUrl(String email, PreSignedUrlRequestDto preSignedUrlRequestDto) {
// 		String fileName = email + preSignedUrlRequestDto + UUID.randomUUID().toString();
// 		String s3Key = email + "/" + preSignedUrlRequestDto.getPictureType().getStringValue();
//
// 		GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket,s3Key, fileName);
//
// 		return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
// 	}
//
// 	private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String s3Key, String fileName) {
// 		GeneratePresignedUrlRequest generatePresignedUrlRequest =
// 			new GeneratePresignedUrlRequest(bucket, fileName)
// 				.withMethod(HttpMethod.PUT)
// 				.withExpiration(getPreSignedUrlExpiration());
// 		generatePresignedUrlRequest.addRequestParameter(
// 			Headers.S3_CANNED_ACL,
// 			CannedAccessControlList.PublicRead.toString());
// 		return generatePresignedUrlRequest;
// 	}
//
// 	private Date getPreSignedUrlExpiration() {
// 		Date expiration = new Date();
// 		long expTimeMillis = expiration.getTime();
// 		expTimeMillis += 1000 * 60 * 2;
// 		expiration.setTime(expTimeMillis);
// 		log.info(expiration.toString());
// 		return expiration;
// 	}
//
// 	public String findByName(String path) {
// 		log.info("Generating signed URL for file name {}", useOnlyOneFileName);
// 		return "https://"+bucket+".s3."+ region +".amazonaws.com/"+path+"/"+useOnlyOneFileName;
// 	}
// }
