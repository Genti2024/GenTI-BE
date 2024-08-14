package com.gt.genti.openfeign.kakao.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.gt.genti.openfeign.kakao.dto.response.KakaoUserResponse;

import lombok.Getter;

@FeignClient(name = "kakaoApiClient", url = "https://kapi.kakao.com")
public interface KakaoApiClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserResponse getUserInformation(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

    // @PostMapping(value="/v1/user/unlink", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // Long unlink(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminKey, @ModelAttribute KakaoUserUnlinkResponseDto kakaoUserUnlinkRequestDto);
}
