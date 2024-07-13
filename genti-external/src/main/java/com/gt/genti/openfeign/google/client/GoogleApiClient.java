package com.gt.genti.openfeign.google.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.gt.genti.openfeign.google.dto.response.GoogleInfoResponse;

@Deprecated
@FeignClient(name = "GoogleApiClient", url = "https://www.googleapis.com")
public interface GoogleApiClient {

    @GetMapping("/oauth2/v3/userinfo")
    GoogleInfoResponse googleInfo(
            @RequestHeader("Authorization") String token
    );

}
