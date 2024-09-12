package com.gt.genti.openchat.controller;

import com.gt.genti.openchat.api.UserOpenChatApi;
import com.gt.genti.openchat.dto.response.OpenChatInfoResponseDto;
import com.gt.genti.openchat.service.OpenChatService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.user.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/open-chat")
@RequiredArgsConstructor
public class UserOpenChatController implements UserOpenChatApi {

    private final OpenChatService openChatService;

    @GetMapping
    public ResponseEntity<ApiResult<OpenChatInfoResponseDto>> getOpenChatUrl(
        @AuthUser Long userId
    ) {
        return GentiResponse.success(openChatService.getOpenChatUrl(userId));
    }

}
