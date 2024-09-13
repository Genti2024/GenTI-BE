package com.gt.genti.openchat.api;

import com.gt.genti.openchat.dto.response.OpenChatInfoResponseDto;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.user.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@AuthorizedUser
@Tag(name = "[UserOpenChatController] 유저의 오픈채팅방 요청", description = "카카오톡 오픈채팅방 정보 요청")
public interface UserOpenChatApi {

    @Operation(summary = "오픈채팅방 정보 조회", description = "오픈채팅방 url과 인원 수를 조회합니다.")
    ResponseEntity<ApiResult<OpenChatInfoResponseDto>> getOpenChatUrl(
        @AuthUser Long userId
    );

}
