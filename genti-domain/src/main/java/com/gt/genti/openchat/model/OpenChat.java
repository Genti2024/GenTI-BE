package com.gt.genti.openchat.model;

import com.gt.genti.common.converter.OpenChatTypeConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "openchat")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenChat {

    @Id
    @Column(name = "type", length = 2)
    @Enumerated(EnumType.STRING)
    private OpenChatType type;

    @Column(name = "count")
    private Long count;

    @Column(name = "url")
    private String url;

    @Builder
    public OpenChat(OpenChatType type, Long count, String url) {
        this.type = type;
        this.count = count;
        this.url = url;
    }

    public void updateCount(Long count) {
        this.count = count;
    }

}
