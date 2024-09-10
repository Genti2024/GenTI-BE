package com.gt.genti.openchat.api;

import com.gt.genti.openchat.model.OpenChat;
import com.gt.genti.openchat.model.OpenChatType;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedAdmin
@Tag(name = "[AdminOpenChatController] 어드민의 오픈채팅방 정보 수정", description = "카카오톡 오픈채팅방 정보 수정")
public interface AdminOpenChatApi {

    @Operation(summary = "오픈채팅방 정보 수정", description = "오픈채팅방 url 및 인원 수를 수정합니다.")
    ResponseEntity<ApiResult<OpenChat>> modifyOpenChatInfo(
        @RequestHeader(value = "Open-Chat-Secret-Key") String secretKey,
        @PathVariable(value = "type") OpenChatType type,
        @RequestParam(required = false) Long count,
        @RequestParam(required = false) String url
    );

}
