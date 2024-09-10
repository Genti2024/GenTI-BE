package com.gt.genti.openchat.model;

import com.gt.genti.common.converter.OpenChatTypeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "openchat")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenChat {

    @Id
    @Convert(converter = OpenChatTypeConverter.class)
    private OpenChatType type;

    private Long count;

    private String url;

}
