package com.gt.genti.picturegeneraterequest.dto.response;

import com.gt.genti.picturegeneraterequest.service.mapper.PictureGenerateRequestStatusForUser;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESFindByUserResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGREQ][User] 사진생성요청 상태 및 사진생성응답 조회 by 사용자 응답 dto")
@Getter
@NoArgsConstructor
public class PGREQStatusResponseDto {
	@Schema(description = "사진생성요청Id", example = "1")
	Long pictureGenerateRequestId;

	@Schema(description = "" +
		"User에게 공개되는 status는 아래 3가지입니다. " + "<br/>" +
		"IN_PROGRESS : 작업이 진행 중" + "<br/>" +
		"AWAIT_USER_VERIFICATION : 완료되었고, 사용자가 확인한 적이 없어 확인을 필요로 함" + "<br/>" +
		"COMPLETED : 완료되었고, 사용자가 이미 확인함")
	PictureGenerateRequestStatusForUser status;

	@Schema(description = "사진생성응답 객체, status 값이 AWAIT_USER_VERIFICATION 가 아니면 null", example = "1", nullable = true)
	PGRESFindByUserResponseDto pictureGenerateResponse;

	@Builder
	public PGREQStatusResponseDto(Long pictureGenerateRequestId, PictureGenerateRequestStatusForUser status,
		PGRESFindByUserResponseDto pgresFindByUserResponseDto) {
		this.pictureGenerateRequestId = pictureGenerateRequestId;
		this.status = status;
		this.pictureGenerateResponse = pgresFindByUserResponseDto;
	}
}
