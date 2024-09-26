package com.gt.genti.userverification.controller;

import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.userverification.api.UserVerificationApi;
import com.gt.genti.userverification.dto.request.UserVerificationRequestDto;
import com.gt.genti.userverification.service.UserVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user-verification")
@RequiredArgsConstructor
public class UserVerificationController implements UserVerificationApi {

    private final UserVerificationService userVerificationService;

    @GetMapping
    public ResponseEntity<ApiResult<Boolean>> checkUserVerification(
            @AuthUser Long userId){
        return GentiResponse.success(userVerificationService.checkUserVerification(userId));
    }

    @PostMapping
    public ResponseEntity<ApiResult<Boolean>> savePictureUserVerification(
            @AuthUser Long userId,
            @RequestBody UserVerificationRequestDto userVerificationRequestDto) {
        return GentiResponse.success(userVerificationService.savePictureUserVerification(userId, userVerificationRequestDto));
    }

}
