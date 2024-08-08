package com.gt.genti.picturegeneraterequest.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQAdminMatchedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQCreatorSubmittedDetailFindByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedAdmin;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.validator.ValidEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AuthorizedAdmin
@Tag(name = "[AdminPGREQController] 어드민 사진생성요청 컨트롤러", description = "사진생성요청을 조회")
public interface AdminPGREQApi {

	@Operation(summary = "어드민에게 매칭된 사진생성요청 전체조회", description = "사진생성요청 전체를 매칭대상(어드민,공급자), 응답의 상태를 조건으로 조회하고  페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Page<PGREQAdminMatchedDetailFindByAdminResponseDto>>> getAllAdminMatchedPGREQ(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "현재 작업(사진생성응답)의 상태로 조회한다. ALL 조회 가능, EXPIRED는 MVP상 도메인로직엔 없지만 어드민페이지의 성격 상 필요하다고 생각하여 추가했습니다.",
			examples = {@ExampleObject(name = "BEFORE_WORK", description = "작업 대기", value = "BEFORE_WORK"),
				@ExampleObject(name = "IN_PROGRESS", description = "작업 중", value = "IN_PROGRESS"),
				@ExampleObject(name = "COMPLETED", description = "작업 완료", value = "COMPLETED"),
				@ExampleObject(name = "EXPIRED", description = "만료됨 (기존의 협의된 내용은 아니지만 어드민페이지에 필요하다고 생각했습니다. 추가 부탁드립니다.)", value = "EXPIRED"),
				@ExampleObject(name = "ALL", description = "전체", value = "ALL")})
		@RequestParam(name = "status", defaultValue = "ALL")
		@ValidEnum(value = PictureGenerateResponseStatusForAdmin.class, hasAllOption = true) String status,
		@Parameter(description = "유저의 email")
		@RequestParam(name = "email", required = false) @Email(message = "올바른 email 형식이 아닙니다.") String email
	);

	@Operation(summary = "공급자->어드민(얼굴붙여야하는 요청)", description = "사진생성요청 전체를 매칭대상(어드민,공급자), 응답의 상태를 조건으로 조회하고  페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto>>> getAllCreatorSubmittedPGREQ(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "현재 작업(사진생성응답)의 상태로 조회한다. ALL 조회 가능, EXPIRED는 MVP상 도메인로직엔 없지만 어드민페이지의 성격 상 필요하다고 생각하여 추가했습니다.",
			examples = {@ExampleObject(name = "BEFORE_WORK", description = "작업 대기", value = "BEFORE_WORK"),
				@ExampleObject(name = "IN_PROGRESS", description = "작업 중", value = "IN_PROGRESS"),
				@ExampleObject(name = "COMPLETED", description = "작업 완료", value = "COMPLETED"),
				@ExampleObject(name = "EXPIRED", description = "만료됨 (기존의 협의된 내용은 아니지만 어드민페이지에 필요하다고 생각했습니다. 추가 부탁드립니다.)", value = "EXPIRED"),
				@ExampleObject(name = "ALL", description = "전체", value = "ALL")})
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = PictureGenerateResponseStatusForAdmin.class, hasAllOption = true) String status,
		@Parameter(description = "유저의 email")
		@RequestParam(name = "email", required = false) @Email(message = "올바른 email 형식이 아닙니다.") String email
	);
}
