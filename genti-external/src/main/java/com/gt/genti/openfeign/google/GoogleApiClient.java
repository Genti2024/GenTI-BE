package com.gt.genti.openfeign.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.gt.genti.openfeign.dto.response.google.GoogleInfoResponse;

@FeignClient(name = "GoogleApiClient", url = "https://www.googleapis.com")
public interface GoogleApiClient {

    @GetMapping("/oauth2/v3/userinfo")
    GoogleInfoResponse googleInfo(
            @RequestHeader("Authorization") String token
    );

}
