package com.gt.genti.openfeign.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.openfeign.dto.response.google.GoogleTokenResponse;

@Deprecated
@FeignClient(name = "GoogleAuthApiClient", url = "https://oauth2.googleapis.com")
public interface GoogleAuthApiClient {

    @PostMapping("/token")
    GoogleTokenResponse googleAuth(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "clientId") String clientId,
            @RequestParam(name = "clientSecret") String clientSecret,
            @RequestParam(name = "redirectUri") String redirectUri,
            @RequestParam(name = "grantType") String grantType);

}
