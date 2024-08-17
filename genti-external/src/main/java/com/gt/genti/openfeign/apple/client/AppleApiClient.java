package com.gt.genti.openfeign.apple.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gt.genti.openfeign.apple.dto.request.AppleTokenRequest;
import com.gt.genti.openfeign.apple.dto.response.ApplePublicKeyResponse;
import com.gt.genti.openfeign.apple.dto.response.AppleTokenRefreshResponse;
import com.gt.genti.openfeign.apple.dto.response.AppleTokenResponse;

@FeignClient(name = "appleApiClient", url = "https://appleid.apple.com/auth")
public interface AppleApiClient {

    @Cacheable(value = "oauthPublicKeyCache", cacheManager = "oauthPublicKeyCacheManager")
    @GetMapping("/keys")
    ApplePublicKeyResponse getApplePublicKeys();

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    AppleTokenResponse getToken(AppleTokenRequest request);

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    AppleTokenRefreshResponse refresh(AppleTokenRefreshRequest request);
}