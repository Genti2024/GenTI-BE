package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자의 내 요청에 의해 완성된 사진 조회 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESFindByUserResponseDto {
	@Schema(description = "사진생성응답 Id", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "완성된 사진 리스트")
	List<CommonPictureResponseDto> pictureCompletedList;

	public PGRESFindByUserResponseDto(PictureGenerateResponse pgres) {
		this.pictureGenerateResponseId = pgres.getId();
		this.pictureCompletedList = pgres.getCompletedPictureList()
			.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}
}
