package com.gt.genti.userverification.controller;

import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.userverification.dto.request.UserVerificationRequestDto;
import com.gt.genti.userverification.service.UserVerificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Deprecated
@ExtendWith(MockitoExtension.class)
public class UserVerificationControllerTest {

    @Mock
    private UserVerificationService userVerificationService;

    @InjectMocks
    private UserVerificationController userVerificationController;

    @Test
    @DisplayName("본인 인증 여부 조회 - 사용자가 이미 본인 인증 상태일 때 성공 상태를 반환")
    void ifUserAlreadyVerified_Then_ReturnTrue() {
        //given
        Long userId = 1L;
        given(userVerificationService.checkUserVerification(userId)).willReturn(TRUE);

        //when
        ResponseEntity<ApiResult<Boolean>> response = userVerificationController.checkUserVerification(userId);

        //then
        verify(userVerificationService, times(1)).checkUserVerification(userId);
        assertSuccessResponse(response, TRUE);
    }

    @Test
    @DisplayName("본인 인증 여부 조회 - 사용자가 본인 인증되지 않은 상태일 때 성공 상태를 반환")
    void ifUserNotVerified_Then_ReturnFalse() {
        //given
        Long userId = 2L;
        given(userVerificationService.checkUserVerification(userId)).willReturn(FALSE);

        //when
        ResponseEntity<ApiResult<Boolean>> response = userVerificationController.checkUserVerification(userId);

        //then
        verify(userVerificationService, times(1)).checkUserVerification(userId);
        assertSuccessResponse(response, FALSE);
    }

    @Test
    @DisplayName("본인 인증 사진 저장 - 저장 성공 시 성공 상태를 반환")
    void savePictureUserVerificationSuccess_Then_ReturnTrue() {
        //given
        Long userId = 1L;
        String key = "USER_VERIFICATION_IMAGE/image.jpg";
        UserVerificationRequestDto requestDto = new UserVerificationRequestDto(key);
        given(userVerificationService.savePictureUserVerification(userId, requestDto)).willReturn(TRUE);

        //when
        ResponseEntity<ApiResult<Boolean>> response = userVerificationController.savePictureUserVerification(userId, requestDto);

        //then
        verify(userVerificationService, times(1)).savePictureUserVerification(userId, requestDto);
        assertSuccessResponse(response, TRUE);
    }

    @Test
    @DisplayName("본인 인증 사진 저장 - 저장 실패 시 성공 상태를 반환") // 성공 상태를 반환하면 안됨, 수정 필요
    void savePictureUserVerificationFail_Then_ReturnFalse() {
        //given
        Long userId = 1L;
        String key = "USER_VERIFICATION_IMAGE/image.jpg";
        UserVerificationRequestDto requestDto = new UserVerificationRequestDto(key);
        given(userVerificationService.savePictureUserVerification(userId, requestDto)).willReturn(FALSE);

        //when
        ResponseEntity<ApiResult<Boolean>> response = userVerificationController.savePictureUserVerification(userId, requestDto);

        //then
        verify(userVerificationService, times(1)).savePictureUserVerification(userId, requestDto);
        assertSuccessResponse(response, FALSE);
    }

    private void assertSuccessResponse(ResponseEntity<ApiResult<Boolean>> response, Boolean status) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // 수정 필요
        ApiResult<Boolean> res = response.getBody();
        Assertions.assertThat(res).isNotNull();
        if (status == TRUE){
            Assertions.assertThat(res.isSuccess()).isTrue();
            Assertions.assertThat(res.getResponse()).isTrue();
        } else {
            Assertions.assertThat(res.isSuccess()).isTrue();
            Assertions.assertThat(res.getResponse()).isFalse();
        }
    }

}
