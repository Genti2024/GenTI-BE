package com.gt.genti.picture.dto.request;

import com.gt.genti.validator.ValidKey;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사진 Key 업데이트 요청 dto")
public class CommonPictureKeyUpdateRequestDto {

	@NotNull
	@ValidKey
	@Schema(description = "사진 key, FileType enum 값으로 시작해야한다.", example = "USER_UPLOADED_IMAGE/**", allowableValues = {
		"USER_UPLOADED_IMAGE/**",
		"CREATED_IMAGE/**",
		"ADMIN_UPLOADED_IMAGE/**",
	})
	String key;
}
