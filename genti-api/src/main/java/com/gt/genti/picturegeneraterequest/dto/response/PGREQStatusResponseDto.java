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
		"User에게 공개되는 status는 아래 4가지입니다. " + "<br/>" +
		"IN_PROGRESS : 작업이 진행 중" + "<br/>" +
		"AWAIT_USER_VERIFICATION : 완료되었고, 사용자가 확인한 적이 없어 확인을 필요로 함" + "<br/>" +
		"CANCELED : 1.공급자가 주문을 받아놓고 회원탈퇴한경우 2.12시간동안 주문을 아무도 받지 않을 경우, 3. 공급자가 주문을 받고 노쇼한 경우 취소되어 FE 분기처리" + "<br/>" +
		"NEW_REQUEST_AVAILABLE : 이전 주문이 완료되었거나 주문한적이 없는 등 새로운 요청 생성이 가능한 상태이다.")
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
