package com.gt.genti.purchase.controller;

import com.gt.genti.purchase.api.UserPurchaseApi;
import com.gt.genti.purchase.dto.request.PurchaseRequestDto;
import com.gt.genti.purchase.service.InAppPurchaseService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.user.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/in-app-purchases")
@RequiredArgsConstructor
public class UserPurchaseController implements UserPurchaseApi {

    private final InAppPurchaseService inAppPurchaseService;

    @PostMapping("/google/receipt-validation")
    public ResponseEntity<GentiResponse.ApiResult<Boolean>> validateReceipt(
            @AuthUser Long userId,
            @RequestBody PurchaseRequestDto purchaseRequestDto) {
        return GentiResponse.success(inAppPurchaseService.validateReceipt(userId, purchaseRequestDto));
    }
}
