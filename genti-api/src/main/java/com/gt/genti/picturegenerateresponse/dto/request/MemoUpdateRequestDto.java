package com.gt.genti.picturegenerateresponse.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Deprecated
@Getter
@NoArgsConstructor
@Schema(description = "메모 수정 요청 Dto")
public class MemoUpdateRequestDto {
	@NotBlank
	@Schema(description = "메모", example = "신고당한적 있음, 까다로운 유저")
	String memo;
}
