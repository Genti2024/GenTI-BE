package com.gt.genti.userverification.api;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.userverification.dto.request.UserVerificationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@AuthorizedUser
@Tag(name = "[UserVerificationController] 유저 본인 인증 컨트롤러", description = "유저의 본인 인증 수행 및 본인 인증 여부를 확인합니다.")
public interface UserVerificationApi {

    @Operation(summary = "본인인증여부확인", description = "유저의 본인 인증 여부를 조회합니다.")
    @EnumResponses(value = {
            @EnumResponse(ResponseCode.OK),
            @EnumResponse(ResponseCode.UserNotFound)
    })
    ResponseEntity<ApiResult<Boolean>> checkUserVerification(
            @AuthUser Long userId);

    @Operation(summary = "본인인증수행", description = "본인인증사진 저장 및 본인인증변경여부를 수정합니다.")
    @EnumResponses(value = {
            @EnumResponse(ResponseCode.OK),
            @EnumResponse(ResponseCode.UserNotFound)
    })
    ResponseEntity<ApiResult<Boolean>> savePictureUserVerification(
            @AuthUser Long userId,
            @RequestBody UserVerificationRequestDto userVerificationRequestDto);
}
