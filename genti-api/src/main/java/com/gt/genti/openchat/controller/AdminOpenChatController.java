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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/open-chat")
@RequiredArgsConstructor
public class AdminOpenChatController implements AdminOpenChatApi {

    private final OpenChatService openChatService;

    @Value("${openchat.admin-secret-key}")
    private String ADMIN_SECRET_KEY;

    @PatchMapping("/{type}")
    public ResponseEntity<ApiResult<OpenChat>> modifyOpenChatInfo(
        @RequestHeader(value = "Admin-Secret-Key") String adminSecretKey,
        @PathVariable(value = "type") String type,
        @RequestParam(value = "count") Long count
    ){
        OpenChatType openChatType = OpenChatType.fromString(type);
        if (!ADMIN_SECRET_KEY.equals(adminSecretKey)) {
            throw ExpectedException.withLogging(ResponseCode.InvalidOpenChatSecretKey);
        } else{
            return GentiResponse.success(openChatService.modifyOpenChatInfo(openChatType, count));
        }
    }
}
