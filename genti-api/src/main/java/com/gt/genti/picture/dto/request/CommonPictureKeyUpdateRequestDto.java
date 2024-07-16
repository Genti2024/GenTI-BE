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
@Schema(name = "[Picture][Anonymous] 사진 업로드 결과 Key 업데이트 요청 dto", description = "사진을 업로드 한 이후 저장한 경로(s3)가 어디인지 서버에 알림")
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
