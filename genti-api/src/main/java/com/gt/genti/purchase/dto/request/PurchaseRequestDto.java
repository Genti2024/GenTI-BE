package com.gt.genti.purchase.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 있어야함
public class PurchaseRequestDto {
    private String packageName; //인앱 상품이 판매된 애플리케이션의 패키지 이름
    private String productId; //인앱 상품 SKU
    private String purchaseToken; //안드로이드에서 받아올 구매 토큰
}
