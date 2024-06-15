package com.gt.genti.dto.common.request;

import com.gt.genti.other.valid.ValidKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonPictureKeyUpdateRequestDto {

	@ValidKey
	String key;
}
