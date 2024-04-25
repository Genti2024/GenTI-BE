// package com.gt.genti.external.aws.controller;
//
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
//
// import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
// import com.gt.genti.external.aws.service.S3Service;
// import com.gt.genti.security.PrincipalDetail;
//
// import lombok.RequiredArgsConstructor;
//
// @Controller
// @RequiredArgsConstructor
// public class S3Controller {
//
// 	private final S3Service s3Service;
//
// 	//관리자 공지사항 등록
//
// 	/**
// 	 * S3에게 pre-signed URL (권한) 요청
// 	 */
// 	@PostMapping("/presigned-url")
// 	public String createPresigned(
// 		@AuthenticationPrincipal PrincipalDetail principalDetail,
// 		@RequestBody PreSignedUrlRequestDto preSignedUrlRequestDto
// 	) {
// 		String email = principalDetail.getUser().getEmail();
// 		return s3Service.getPreSignedUrl(email, preSignedUrlRequestDto);
// 	}
// }