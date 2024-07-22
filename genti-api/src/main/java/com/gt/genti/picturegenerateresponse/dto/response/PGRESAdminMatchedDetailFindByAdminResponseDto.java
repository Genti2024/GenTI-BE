package com.gt.genti.picturegenerateresponse.dto.response;

import java.util.List;

import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGRES][Admin] 어드민에게 바로 매칭된 요청 사진생성응답 조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESAdminMatchedDetailFindByAdminResponseDto {
	@Schema(description = "사진생성응답의 DB Id", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "어드민이 생성한 사진 리스트")
	List<CommonPictureResponseDto> pictureCompletedList;
	@Schema(description = "사진생성응답의 상태(작업 대기, 작업 중, 작업 완료, 만료됨)", example = "IN_PROGRESS")
	PictureGenerateResponseStatusForAdmin responseStatus;

	@Builder
	public PGRESAdminMatchedDetailFindByAdminResponseDto(Long pictureGenerateResponseId, String memo,
		List<CommonPictureResponseDto> pictureCompletedList,
		PictureGenerateResponseStatusForAdmin status) {
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.pictureCompletedList = pictureCompletedList;
		this.responseStatus = status;
	}
}
