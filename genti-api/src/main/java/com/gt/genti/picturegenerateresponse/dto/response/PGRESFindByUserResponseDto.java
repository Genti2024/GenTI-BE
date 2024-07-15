package com.gt.genti.picturegenerateresponse.dto.response;

import java.util.List;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGRES][User] 사진생성응답 조회 by 사용자 응답 dto")
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
			.map(CommonPictureResponseDto::of)
			.toList();
	}
}
