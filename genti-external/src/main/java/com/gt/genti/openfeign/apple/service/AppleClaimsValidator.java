package com.gt.genti.openfeign.apple.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppleClaimsValidator {

    private static final String NONCE_KEY = "nonce";
    private final String iss = "https://appleid.apple.com";
    private final String clientId;
    private final String nonce;

    public AppleClaimsValidator(
            @Value("${apple.client-id}") String clientId,
            @Value("${apple.nonce}") String nonce
    ) {
        this.clientId = clientId;
        this.nonce = encrypt(nonce);
    }

    public boolean isValid(Claims claims) {
        log.info(claims.toString());
        if(claims.getIssuer().contains(iss)){
            log.info("iss 가 같지않음");
        }
        if(claims.getAudience().equals(clientId)){
            log.info("clientid 가 같지않음");
        }
        if(claims.get(NONCE_KEY, String.class).equals(nonce)){
            log.info("nonce 변조");
        }
        return claims.getIssuer().contains(iss) &&
                claims.getAudience().equals(clientId) &&
                claims.get(NONCE_KEY, String.class).equals(nonce);
    }

    public static String encrypt(String value) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha256.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw ExpectedException.withLogging(ResponseCode.EncryptAlgorithmDeprecated, "Apple Oauth2 로그인");
        }
    }
}