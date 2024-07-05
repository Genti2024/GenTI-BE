package com.gt.genti.dto.user.response;

import com.gt.genti.domain.enums.PictureGenerateRequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저의 사진생성요청 현재 상태 조회 응답 dto")
@Getter
@NoArgsConstructor
public class PGREQStatusResponseDto {
	@Schema(description = "사진생성요청Id", example = "1")
	Long pictureGenerateRequestId;
	@Schema(description = "" +
		"사진생성요청진행상태 " + "<br/>" +
		"진행중 : IN_PROGRESS, ASSIGNING, CREATED" + "<br/>" +
		"완성되었지만 유저가 확인하지 않음(완성된 사진 조회를 요청해야하는경우) : AWAIT_USER_VERIFICATION" + "<br/>" +
		"유저가 확인한 완성된 상태 : COMPLETED")
	PictureGenerateRequestStatus status;

	@Builder
	public PGREQStatusResponseDto(Long pictureGenerateRequestId, PictureGenerateRequestStatus status) {
		this.pictureGenerateRequestId = pictureGenerateRequestId;
		this.status = status;
	}
}
