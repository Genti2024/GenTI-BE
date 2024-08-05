package com.gt.genti.firebase.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FcmTokenSaveOrUpdateRequestDto {

  private final String token;
  private final Long userId;
}