package com.gt.genti.purchase.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseRequestDto {
    private String packageName;
    private String productId;
    private String purchaseToken;
}
