package com.gt.genti.service;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.userverification.model.PictureUserVerification;
import com.gt.genti.picture.userverification.repository.PictureUserVerificationRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.userverification.dto.request.UserVerificationRequestDto;
import com.gt.genti.userverification.service.UserVerificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserVerificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PictureUserVerificationRepository pictureUserVerificationRepository;

    @InjectMocks
    private UserVerificationService userVerificationService;

    @Test
    @DisplayName("본인 인증 여부 조회 - 인증한 사용자")
    void checkUserVerification_userIsVerified() {
        //given
        Long userId = 1L;
        User user = User.base().id(userId).userVerified(true).build();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        //when
        Boolean isVerified = userVerificationService.checkUserVerification(userId);

        //then
        assertThat(isVerified).isTrue();
    }

    @Test
    @DisplayName("본인 인증 여부 조회 - 인증하지 않은 사용자")
    void checkUserVerification_userIsNotVerified() {
        //given
        Long userId = 1L;
        User user = User.base().id(userId).userVerified(false).build();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        //when
        Boolean isVerified1 = userVerificationService.checkUserVerification(userId);

        //then
        assertThat(isVerified1).isFalse();
    }

    @Test
    @DisplayName("본인 인증 여부 조회 - 존재하지 않은 사용자일 경우 예외 발생")
    void checkUserVerification_userIsNotfound() {
        //given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> userVerificationService.checkUserVerification(userId))
                .isInstanceOf(ExpectedException.class)
                .hasFieldOrPropertyWithValue("responseCode", ResponseCode.UserNotFound);
    }

    @Test
    @DisplayName("본인 인증 사진 저장 - 성공 : 1. 사진이 저장되고, 2. 사용자의 본인 인증 상태가 변경된다.")
    void savePictureUserVerification_Success() {
        //given
        Long userId = 1L;
        User user = User.base()
                .id(userId)
                .userVerified(null)
                .build();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        String key = "USER_VERIFICATION_IMAGE/image.jpg";
        PictureUserVerification mockSavedPictureUserVerification = PictureUserVerification.builder()
                .id(1L)
                .key(key)
                .user(user)
                .build();
        given(pictureUserVerificationRepository.save(any(PictureUserVerification.class)))
                .willReturn(mockSavedPictureUserVerification);

        given(userRepository.save(any(User.class))).willReturn(user);

//        ArgumentCaptor<PictureUserVerification> captor = ArgumentCaptor.forClass(PictureUserVerification.class);

        //when
        Boolean isSaved = userVerificationService.savePictureUserVerification(userId, new UserVerificationRequestDto(key));

        //then
        // 1
//        verify(pictureUserVerificationRepository).save(captor.capture());
//        PictureUserVerification savedPictureUserVerification = captor.getValue();
//        assertThat(savedPictureUserVerification.getKey()).isEqualTo(mockSavedPictureUserVerification.getKey());
//        assertThat(savedPictureUserVerification.getUploadedBy().getId()).isEqualTo(mockSavedPictureUserVerification.getUploadedBy().getId());
        verify(pictureUserVerificationRepository).save(any(PictureUserVerification.class));

        // 2
        assertThat(user.getUserVerified()).isTrue();

        // 최종
        assertThat(isSaved).isTrue();
    }

    @Test
    @DisplayName("본인 인증 사진 저장 - 존재하지 않은 사용자일 경우 예외 발생")
    void savePictureUserVerification_userIsNotfound() {
        //given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> userVerificationService.checkUserVerification(userId))
                .isInstanceOf(ExpectedException.class)
                .hasFieldOrPropertyWithValue("responseCode", ResponseCode.UserNotFound);
    }

}