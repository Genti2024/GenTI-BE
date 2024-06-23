package com.gt.genti.dto.common.request;

import com.gt.genti.other.valid.ValidKey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")

public class CommonPictureKeyUpdateRequestDto {

	@ValidKey
	@Schema(name = "key")
	String key;
}
