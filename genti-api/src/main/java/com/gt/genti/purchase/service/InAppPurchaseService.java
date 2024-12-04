package com.gt.genti.purchase.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.purchase.dto.request.PurchaseRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@Service
public class InAppPurchaseService {

    @Value("${google-account.file-path}")
    private String googleAccountFilePath;

    @Value("${google-application.package-name}")
    private String googleApplicationPackageName;

    public Boolean validateReceipt(Long userId, PurchaseRequestDto purchaseRequestDto) {

//        try {
            // ================= Google Credential 생성 =================

        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

        AndroidPublisher.Builder builder;
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            InputStream inputStream = new ClassPathResource(googleAccountFilePath).getInputStream();
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(inputStream)
                    .createScoped(AndroidPublisherScopes.ANDROIDPUBLISHER);
            builder = new AndroidPublisher.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials));
        } catch (IOException | GeneralSecurityException e){
            throw ExpectedException.withLogging(ResponseCode.CashoutNotFound,
                "---------------------------------------구매 에러 IOException, GeneralSecurityException 에러 1111111111: " + e + "-------------------------------------------------");
        }

        AndroidPublisher publisher;
        try {
            // ======================== API 호출 ========================
            publisher = builder.setApplicationName(googleApplicationPackageName).build();
            AndroidPublisher.Purchases.Products.Get gas = publisher.purchases()
                    .products()
                    .get(
                            "packageName",
                            "productId",
                            "purchaseToken");
            ProductPurchase purchase = gas.execute();
        } catch (IOException e){
            throw ExpectedException.withLogging(ResponseCode.HttpMessageNotReadable,
                    "---------------------------------------구매 에러 IOException 에러 2222222222: " + e + "-------------------------------------------------");
        }


        try{
            AndroidPublisher.Purchases.Products.Get get = publisher.purchases().products()
                    .get(purchaseRequestDto.getPackageName(), purchaseRequestDto.getProductId(), purchaseRequestDto.getPurchaseToken()); //inapp 아이템의 구매 및 소모 상태 확인
            ProductPurchase productPurchase = get.execute(); //검증 결과
            System.out.println(productPurchase.toPrettyString());

            // 인앱 상품의 소비 상태. 0 아직 소비 안됨(Yet to be consumed) / 1 소비됨(Consumed)
            Integer consumptionState = productPurchase.getConsumptionState();

            // 개발자가 지정한 임의 문자열 정보
            String developerPayload = productPurchase.getDeveloperPayload();

            // 구매 상태. 0 구매완료 / 1 취소됨
            Integer purchaseState = productPurchase.getPurchaseState();
            if(purchaseState == 1){
                return false;
            }

            // 상품이 구매된 시각. 타임스탬프 형태
            Long purchaseTimeMillis = productPurchase.getPurchaseTimeMillis();

            return true;
        } catch (IOException e) {
            throw ExpectedException.withLogging(ResponseCode.FileTypeNotProvided,
                    "---------------------------------------구매 에러 IOException 에러 333333333333: " + e + "-------------------------------------------------");
        }
    }
}
