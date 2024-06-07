package com.gt.genti.dto.common;

import com.gt.genti.other.valid.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonPictureKeyUpdateRequestDto {

	@Key
	String key;
}
