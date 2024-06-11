package com.gt.genti.dto.common.request;

import com.gt.genti.other.valid.ValidKey;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonPictureKeyUpdateRequestDto {

	@ValidKey
	String key;
}
