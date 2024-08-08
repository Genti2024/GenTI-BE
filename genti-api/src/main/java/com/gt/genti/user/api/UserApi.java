package com.gt.genti.user.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.auth.dto.request.SignUpRequestDTO;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.dto.request.UserInfoUpdateRequestDto;
import com.gt.genti.user.dto.response.UserFindResponseDto;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AuthorizedUser
@Tag(name = "[UserController] 유저 컨트롤러", description = "유저의 정보를 조회,수정합니다.")
public interface UserApi {

	@Operation(summary = "내정보보기", description = "유저의 정보를 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)
	})
	ResponseEntity<ApiResult<UserFindResponseDto>> getUserInfo(
		@AuthUser Long userId);

	@Operation(summary = "내정보 수정", description = "유저의 정보를 수정합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)

	})
	ResponseEntity<ApiResult<UserFindResponseDto>> updateUserInfo(
		@AuthUser Long userId,
		@RequestBody @Valid UserInfoUpdateRequestDto userInfoUpdateRequestDto);

	@Operation(summary = "최초가입 정보등록", description = "사용자에게 생년, 성별을 받아 최종 가입을 처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserAlreadySignedUp)
	})
	ResponseEntity<ApiResult<Boolean>> signUp(
		@AuthUser Long userId,
		@RequestBody @Valid SignUpRequestDTO signUpRequestDTO);

	@Operation(summary = "로그아웃", description = "refreshToken 삭제")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.Forbidden),
		@EnumResponse(ResponseCode.REFRESH_TOKEN_NOT_EXISTS),
	})
	ResponseEntity<ApiResult<Boolean>> logout(@AuthUser Long userId);

	@Operation(summary = "회원 복구", description = "회원탈퇴 취소 처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound),
		@EnumResponse(ResponseCode.CannotRestoreUser)

	})
	ResponseEntity<ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthUser Long userId);

	@Operation(summary = "회원 탈퇴", description = "사용자 정보 및 관련 정보를 모두 삭제(복구 불가)")
	@EnumResponses(value = {
			@EnumResponse(ResponseCode.OK),
			@EnumResponse(ResponseCode.UserNotFound)
	})
	public ResponseEntity<ApiResult<Boolean>> deleteUserHard(
			@AuthUser Long userId);

	@Operation(summary = "내 사진 전체조회 - Pagination", description = "내가 사진생성요청으로 생성된 사진 전체 조회 Pagination")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)
	})
	ResponseEntity<ApiResult<Page<CommonPictureResponseDto>>> getAllMyGeneratedPicture(
		@AuthUser Long userId,
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {
			"id", "createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	);

	@Operation(summary = "내 사진 전체조회", description = "내가 사진생성요청으로 생성된 사진 전체 조회")
	@EnumResponses(value = {
			@EnumResponse(ResponseCode.OK),
			@EnumResponse(ResponseCode.UserNotFound)
	})
	ResponseEntity<ApiResult<List<CommonPictureResponseDto>>> getAllMyGeneratedPictureNoPage(
			@AuthUser Long userId
	);
}
