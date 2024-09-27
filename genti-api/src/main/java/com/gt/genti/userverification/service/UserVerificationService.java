package com.gt.genti.userverification.service;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.userverification.model.PictureUserVerification;
import com.gt.genti.picture.userverification.repository.PictureUserVerificationRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.userverification.dto.request.UserVerificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserVerificationService {

    private final UserRepository userRepository;
    private final PictureUserVerificationRepository pictureUserVerificationRepository;

    public Boolean checkUserVerification(Long userId){
        User foundUser = getUserByUserId(userId);
        Boolean isVerified = foundUser.getUserVerified();
        return isVerified != null && isVerified;
    }

    public Boolean savePictureUserVerification(Long userId, UserVerificationRequestDto userVerificationRequestDto){
        User foundUser = getUserByUserId(userId);
        PictureUserVerification pictureUserVerification = new PictureUserVerification(userVerificationRequestDto.getKey(), foundUser);
        pictureUserVerificationRepository.save(pictureUserVerification);
        foundUser.verifyUser();
        return true;
    }


    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
    }

}
