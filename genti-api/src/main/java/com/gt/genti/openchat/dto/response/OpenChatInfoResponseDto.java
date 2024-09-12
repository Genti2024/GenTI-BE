package com.gt.genti.openchat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[OpenChat][User] 오픈채팅방 정보 응답 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenChatInfoResponseDto {

    @Schema(description = "오픈 채팅방 대상 여부", example = "true")
    Boolean accessible;

    @Schema(description = "오픈 채팅방 사람 수")
    Long count;

    @Schema(description = "오픈 채팅방 url")
    String url;

    @Builder
    public OpenChatInfoResponseDto(Boolean accessible, Long count, String url) {
        this.accessible = accessible;
        this.count = count;
        this.url = url;
    }
}
