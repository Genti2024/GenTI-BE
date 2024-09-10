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
import org.springframework.stereotype.Service;

import static com.gt.genti.openchat.model.OpenChatType.*;

@Service
@RequiredArgsConstructor
public class OpenChatService {
    private final OpenChatRepository openChatRepository;
    private final UserRepository userRepository;

    public OpenChatInfoResponseDto getOpenChatUrl(Long userId){
        User foundUser = getUserById(userId);
        int userBirthYear = parseBirthYear(foundUser.getBirthYear());
        OpenChatType openChatType = getOpenChatType(userBirthYear);

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

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
    }

    private int parseBirthYear(String birthYear) {
        try {
            return Integer.parseInt(birthYear);
        } catch (NumberFormatException e) {
            throw ExpectedException.withLogging(ResponseCode.InvalidBirthYearFormat, birthYear);
        }
    }

    private OpenChatType getOpenChatType(int userBirthYear) {
        if (userBirthYear >= 2001) {
            return YB;
        } else if (userBirthYear <= 1974) {
            return OB;
        } else {
            return NONE;
        }
    }
}
