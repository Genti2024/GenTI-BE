package com.gt.genti.user.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.discord.SignUpEvent;
import com.gt.genti.user.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignUpEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publishSignUpEvent(User user) {
        eventPublisher.publishEvent(SignUpEvent.of(
                user.getId(),
                user.getUsername(),
                user.getEmail() == null ? "" : user.getEmail(),
                user.getLastLoginOauthPlatform().toString(),
                user.getCreatedAt()
        ));
    }

}
