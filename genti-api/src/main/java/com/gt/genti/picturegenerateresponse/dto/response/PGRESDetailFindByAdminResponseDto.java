package com.gt.genti.picturegenerateresponse.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGRES][Admin] 사진생성응답 조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESDetailFindByAdminResponseDto {
	@Schema(description = "사진생성응답 응답 Dto", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "메모", example = "제일 먼저 할 것")
	String memo;
	@Schema(description = "공급자 email", example = "example@google.com")
	String creatorEmail;
	@Schema(description = "공급자가 생성한 사진 리스트")
	List<CommonPictureResponseDto> pictureCreatedByCreatorList;
	@Schema(description = "어드민이 생성한 사진 리스트")
	List<CommonPictureResponseDto> pictureCompletedList;
	@Schema(description = "사진생성응답의 상태(작업 대기, 작업 중, 완료)", example = "IN_PROGRESS")
	PictureGenerateResponseStatusForAdmin responseStatus;
	@Schema(description = "생성일시")
	LocalDateTime createdAt;

	@Builder
	public PGRESDetailFindByAdminResponseDto(Long pictureGenerateResponseId, String memo,
		String creatorEmail, List<CommonPictureResponseDto> pictureCompletedList,
		List<CommonPictureResponseDto> pictureCreatedByCreatorList,
		PictureGenerateResponseStatusForAdmin status, LocalDateTime createdAt) {
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.memo = memo;
		this.creatorEmail = creatorEmail;
		this.pictureCompletedList = pictureCompletedList;
		this.pictureCreatedByCreatorList = pictureCreatedByCreatorList;
		this.responseStatus = status;
		this.createdAt = createdAt;
	}
}
