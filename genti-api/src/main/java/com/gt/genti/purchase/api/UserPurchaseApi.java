package com.gt.genti.purchase.api;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.purchase.dto.request.PurchaseRequestDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@AuthorizedUser
@Tag(name = "[UserPurchaseController] 인앱 결제 컨트롤러", description = "결제 내용을 서버에서 검증합니다.")
public interface UserPurchaseApi {

    @Operation(summary = "영수증 검증", description = "클라이언트로부터 받은 결제 내역을 검증합니다.")
    @EnumResponses(value = {
            @EnumResponse(ResponseCode.OK),
            @EnumResponse(ResponseCode.UserNotFound)
    })
    ResponseEntity<GentiResponse.ApiResult<Boolean>> validateReceipt(
            @AuthUser Long userId,
            @RequestBody PurchaseRequestDto purchaseRequestDto);
}
