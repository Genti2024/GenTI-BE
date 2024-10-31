package com.gt.genti.picturegenerateresponse.api;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedUser
@Tag(name = "[FrontendPGRESController] 프론트엔드 개발자가 직접 사진 생성 응답 처리하기", description = "개발 서버 한정입니다.")
public interface FrontendPGRESApi {

    @Operation(summary = "사진 생성 응답 처리", description = "사진 생성 응답을 처리합니다.")
    @EnumResponses(value = {
            @EnumResponse(ResponseCode.OK)
    })
    ResponseEntity<GentiResponse.ApiResult<Boolean>> finishPGRESByFrontend(
        @AuthUser Long userId);

}
