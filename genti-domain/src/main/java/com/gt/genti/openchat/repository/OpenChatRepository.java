package com.gt.genti.openchat.repository;

import com.gt.genti.openchat.model.OpenChat;
import com.gt.genti.openchat.model.OpenChatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenChatRepository extends JpaRepository<OpenChat, OpenChatType> {
    OpenChat findByType(OpenChatType type);
}
