package com.gt.genti.openchat.controller;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.openchat.api.AdminOpenChatApi;
import com.gt.genti.openchat.model.OpenChat;
import com.gt.genti.openchat.model.OpenChatType;
import com.gt.genti.openchat.service.OpenChatService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/open-chat")
@RequiredArgsConstructor
public class AdminOpenChatController implements AdminOpenChatApi {

    private final OpenChatService openChatService;
    private final String API_KEY = "임시-프로퍼티파일로옮길예정입니다";

    @PatchMapping("/{type}")
    public ResponseEntity<ApiResult<OpenChat>> modifyOpenChatInfo(
        @RequestHeader(value = "Open-Chat-Secret-Key") String secretKey,
        @PathVariable(value = "type") OpenChatType type,
        @RequestParam(required = false) Long count,
        @RequestParam(required = false) String url
    ){
        if (!API_KEY.equals(secretKey)) {
            throw ExpectedException.withLogging(ResponseCode.InvalidOpenChatSecretKey);
        } else{
            return GentiResponse.success(openChatService.modifyOpenChatInfo(type, count, url));
        }
    }
}
