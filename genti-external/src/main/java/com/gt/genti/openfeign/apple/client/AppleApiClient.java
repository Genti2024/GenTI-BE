package com.gt.genti.openfeign.apple.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.gt.genti.openfeign.apple.dto.response.ApplePublicKeys;

@FeignClient(name = "appleApiClient", url = "https://appleid.apple.com/auth")
public interface AppleApiClient {

    @Cacheable(value = "oauthPublicKeyCache", cacheManager = "oauthPublicKeyCacheManager")
    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKeys();
}