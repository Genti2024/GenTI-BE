package com.gt.genti.picturegenerateresponse.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGRES][Admin] 공급자->어드민(얼굴붙여야하는작업) 사진생성응답 조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESCreatorSubmittedDetailFindByAdminResponseDto {
	@Schema(description = "작업넘버(사진생성응답 DB Id)", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "공급자 email", example = "example@google.com")
	String creatorEmail;
	@Schema(description = "공급자가 제출한 사진 리스트")
	List<CommonPictureResponseDto> pictureCreatedByCreator;
	@Schema(description = "어드민이 제출한 사진 리스트")
	List<CommonPictureResponseDto> pictureCompletedList;
	@Schema(description = "사진생성응답의 상태(작업 대기, 작업 중, 완료, (만료됨))", example = "IN_PROGRESS")
	PictureGenerateResponseStatusForAdmin responseStatus;
	@Schema(description = "공급자->어드민 제출일시")
	LocalDateTime submittedByCreatorAt;

	@Builder
	public PGRESCreatorSubmittedDetailFindByAdminResponseDto(Long pictureGenerateResponseId, String memo,
		String creatorEmail, List<CommonPictureResponseDto> pictureCreatedByCreator,
		List<CommonPictureResponseDto> pictureCompletedList,
		PictureGenerateResponseStatusForAdmin responseStatus, LocalDateTime submittedByCreatorAt) {
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.creatorEmail = creatorEmail;
		this.pictureCreatedByCreator = pictureCreatedByCreator;
		this.pictureCompletedList = pictureCompletedList;
		this.responseStatus = responseStatus;
		this.submittedByCreatorAt = submittedByCreatorAt;
	}
}
