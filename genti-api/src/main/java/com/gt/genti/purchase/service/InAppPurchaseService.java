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

        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

        AndroidPublisher.Builder builder;
        HttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (IOException | GeneralSecurityException e1) {
            throw ExpectedException.withLogging(ResponseCode.HttpMessageNotReadable, e1);
        }

        InputStream inputStream;
        try {
            inputStream = new ClassPathResource(googleAccountFilePath).getInputStream();
        } catch (IOException e2) {
            throw ExpectedException.withLogging(ResponseCode.HandlerNotFound, e2);
        }

        GoogleCredentials credentials;
        try {
            credentials = GoogleCredentials
                    .fromStream(inputStream)
                    .createScoped(AndroidPublisherScopes.ANDROIDPUBLISHER);
        } catch (IOException e3) {
            throw ExpectedException.withLogging(ResponseCode.AlreadyActivatedUser, e3);
        }

        builder = new AndroidPublisher.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials));

        AndroidPublisher publisher = null;
        publisher = builder.setApplicationName(googleApplicationPackageName).build();

        try {
            AndroidPublisher.Purchases.Products.Get get = publisher.purchases().products()
                    .get(purchaseRequestDto.getPackageName(), purchaseRequestDto.getProductId(), purchaseRequestDto.getPurchaseToken());
            ProductPurchase productPurchase = get.execute(); //검증 결과
            System.out.println(productPurchase.toPrettyString());

            // 인앱 상품의 소비 상태. 0 아직 소비 안됨(Yet to be consumed) / 1 소비됨(Consumed)
            Integer consumptionState = productPurchase.getConsumptionState();

            // 상품이 구매된 시각. 타임스탬프 형태
            Long purchaseTimeMillis = productPurchase.getPurchaseTimeMillis();

            // 구매 상태. 0 구매완료 / 1 취소됨
            Integer purchaseState = productPurchase.getPurchaseState();
            if (purchaseState == 1) {
                return false;
            }
            return true;
        } catch (IOException e5) {
            throw ExpectedException.withLogging(ResponseCode.FileTypeNotProvided, e5);
        }
    }
}