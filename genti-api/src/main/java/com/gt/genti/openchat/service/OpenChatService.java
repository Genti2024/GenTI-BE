package com.gt.genti.openchat.service;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.openchat.dto.response.OpenChatInfoResponseDto;
import com.gt.genti.openchat.model.OpenChat;
import com.gt.genti.openchat.model.OpenChatType;
import com.gt.genti.openchat.repository.OpenChatRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gt.genti.openchat.model.OpenChatType.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenChatService {
    private final OpenChatRepository openChatRepository;
    private final UserRepository userRepository;

    public OpenChatInfoResponseDto getOpenChatUrl(Long userId){
        User foundUser = getUserById(userId);
        OpenChatType openChatType = getOpenChatType(foundUser);

        if(openChatType == YB || openChatType == OB) {
            OpenChat openChat = openChatRepository.findByType(openChatType);
            return OpenChatInfoResponseDto.builder()
                .accessible(true)
                .url(openChat.getUrl())
                .count(openChat.getCount())
                .build();
        } else {
            return OpenChatInfoResponseDto.builder()
                .accessible(false)
                .url(null)
                .count(null)
                .build();
        }
    }

    public OpenChat modifyOpenChatInfo(OpenChatType type, Long count){
        OpenChat openChat = getOpenChatInfoByType(type);
        openChat.updateCount(count);
        return openChatRepository.save(openChat);
    }

    private OpenChat getOpenChatInfoByType(OpenChatType type){
        OpenChat openChat = openChatRepository.findByType(type);
        if (openChat == null) {
            throw ExpectedException.withLogging(ResponseCode.OpenChatNotFound, type);
        }
        return openChat;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
    }

    private OpenChatType getOpenChatType(User user) {
        try{
            int userBirthYear = Integer.parseInt(user.getBirthYear());
            if (userBirthYear >= 2001) {
                return YB;
            } else if (userBirthYear <= 1974) {
                return OB;
            } else {
                return null;
            }
        } catch (NumberFormatException | NullPointerException e) {
            log.info("사용자 생년의 숫자 변환 시 문제가 발생 : {}", e.getMessage(), e);
            return null;
        }
    }
}
