package com.gt.genti;

import static com.gt.genti.TestUtils.*;
import static com.gt.genti.TestUtils.Dto.*;
import static com.gt.genti.picturegeneraterequest.service.mapper.PictureGenerateRequestStatusForUser.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.aws.FileType;
import com.gt.genti.config.TestConfig;
import com.gt.genti.matchingstrategy.model.RequestMatchStrategy;
import com.gt.genti.picturegeneraterequest.controller.CreatorPGREQController;
import com.gt.genti.picturegeneraterequest.controller.UserPGREQController;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegeneraterequest.service.RequestMatchService;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.report.service.ReportService;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserService;

@ActiveProfiles({"common", "secret", "test"})
@SpringBootTest(classes = TestConfig.class)
class PictureGeneratePendingApiTest {

	@Autowired
	UserPGREQController userPGREQController;
	@Autowired
	PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Autowired
	PictureGenerateWorkService pictureGenerateWorkService;

	@Autowired
	UserService userService;

	@Autowired
	ReportService reportService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	PictureGenerateResponseRepository pictureGenerateResponseRepository;

	@Autowired
	PictureGenerateRequestRepository pictureGenerateRequestRepository;
	@Autowired
	private CreatorPGREQController creatorPGREQController;
	@Autowired
	RequestMatchService requestMatchService;

	@Transactional
	@Test
	@DisplayName("방금 생성하고 어드민에게 매칭된 사진생성요청은 유저입장에서 IN_PROGRESS로 보이는지 테스트")
	void newlyCreatedAndMatchedToAdminPictureGenerateRequestIsInProgresstest() {
		// given
		// 어드민유저, 어드민 공급자 생성
		User adminUser = Domain.createUser(UserRole.ADMIN);
		User savedAdminUser = userRepository.save(adminUser);
		userService.updateUserRole(savedAdminUser.getId(), getUserRoleUpdateRequestDto(UserRole.ADMIN));

		// 요청을 수행할 유저 생성
		User newUser = Domain.createUser(UserRole.USER);
		User savedUser = userRepository.save(newUser);

		// 사진생성요청 생성 dto
		PGREQSaveRequestDto pgreqSaveRequestDto = Dto.getPGREQSaveRequestDto();

		// when
		// 사진생성요청 생성
		PictureGenerateRequest pgreq = pictureGenerateRequestUseCase.createPGREQ(savedUser.getId(),
			pgreqSaveRequestDto.toCommand());

		// 사진생성요청의 상태 조회
		PGREQStatusResponseDto pgreqStatusResponseDto = pictureGenerateRequestUseCase.getPendingPGREQStatusIfExists(
			newUser.getId());

		// then
		assertThat(pgreqStatusResponseDto.getPictureGenerateRequestId()).isEqualTo(pgreq.getId());
		assertThat(pgreqStatusResponseDto.getStatus()).isEqualTo(IN_PROGRESS);
		assertThat(pgreqStatusResponseDto.getPictureGenerateResponse()).isNull();

	}

	@Transactional
	@Test
	@DisplayName("만약 유저가 이전 사진생성요청에 대한 응답을 신고한 경우 새로운 요청을 수행할 수 있음")
	void IfUserReportsRecentPictureGenerateRequestThenNew_REQUEST_AVAILABLE_Test() {
		// given
		// 어드민유저, 어드민 공급자 생성
		User adminUser = Domain.createUser(UserRole.ADMIN);
		User savedAdminUser = userRepository.save(adminUser);
		userService.updateUserRole(savedAdminUser.getId(), Dto.getUserRoleUpdateRequestDto(UserRole.ADMIN));

		// 요청을 수행할 유저 생성
		User newUser = Domain.createUser(UserRole.USER);
		User savedUser = userRepository.save(newUser);

		// 사진생성요청 생성 dto
		PGREQSaveRequestDto pgreqSaveRequestDto = Dto.getPGREQSaveRequestDto();

		// when
		// 사진생성요청 생성
		PictureGenerateRequest pgreq = pictureGenerateRequestUseCase.createPGREQ(savedUser.getId(),
			pgreqSaveRequestDto.toCommand());

		// 어드민에게 매칭된 요청에서 생성된 사진생성응답 추출
		PictureGenerateRequest matchedPGREQ = pictureGenerateRequestRepository.findById(pgreq.getId()).orElseThrow();
		PictureGenerateResponse pgresBeforeWork = matchedPGREQ.getResponseList().get(0);

		// 어드민이 사진생성응답에 사진 업로드
		pictureGenerateWorkService.updatePictureListCreatedByAdmin(savedAdminUser.getId(),
			List.of(getCommonPictureKeyUpdateRequestDto(
				FileType.ADMIN_UPLOADED_IMAGE)), pgresBeforeWork.getId());

		// 어드민이 최종 제출 처리
		pictureGenerateWorkService.submitFinal(pgresBeforeWork.getId());

		// 유저가 해당 응답을 신고
		reportService.createReport(newUser.getId(), getReportCreateRequestDto(pgresBeforeWork.getId()));

		// 사진생성요청의 상태 조회
		PGREQStatusResponseDto pgreqStatusResponseDto = pictureGenerateRequestUseCase.getPendingPGREQStatusIfExists(
			newUser.getId());

		// then
		// 현재 유저는 NEW_REQUEST_AVAILABLE를 응답받음
		assertThat(pgreqStatusResponseDto.getStatus()).isEqualTo(NEW_REQUEST_AVAILABLE);

		// 사진생성응답은 REPORTED 상태
		PictureGenerateResponse pgresAdminSubmitted = pictureGenerateResponseRepository.findById(
				pgresBeforeWork.getId())
			.orElseThrow();
		assertThat(pgresAdminSubmitted.getStatus()).isEqualTo(PictureGenerateResponseStatus.REPORTED);

		// 사진생성요청은 REPORTED 상태
		PictureGenerateRequest pgreqAdminSubmitted = pictureGenerateRequestRepository.findById(matchedPGREQ.getId())
			.orElseThrow();
		assertThat(pgreqAdminSubmitted.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.REPORTED);
	}

	@Transactional
	@Test
	void pictureGenerateRequestCancelledByCreatorExitTest() {
		// given
		// 공급자 유저 생성
		User creatorUser = Domain.createUser(UserRole.CREATOR);
		User savedCreatorUser = userRepository.save(creatorUser);
		userService.updateUserRole(savedCreatorUser.getId(), getUserRoleUpdateRequestDto(UserRole.CREATOR));

		User roleUpdatedUser = userRepository.findById(savedCreatorUser.getId()).orElseThrow();

		// 요청을 수행할 유저 생성
		User newUser = Domain.createUser(UserRole.USER);
		User savedUser = userRepository.save(newUser);

		// 사진생성요청 생성 dto
		PGREQSaveRequestDto pgreqSaveRequestDto = getPGREQSaveRequestDto();

		// 공급자에게 매칭되기위해 매칭전략 변경
		requestMatchService.changeMatchingStrategy(RequestMatchStrategy.CREATOR_ADMIN);

		// when
		// 사진생성요청 생성
		pictureGenerateRequestUseCase.createPGREQ(savedUser.getId(), pgreqSaveRequestDto.toCommand());

		// 공급자에게 매칭된 요청에서 생성된 사진생성응답 추출
		ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> resultResponseEntity = creatorPGREQController.getAssignedPictureGenerateRequestBrief(
			roleUpdatedUser.getId());

		PGREQBriefFindByCreatorResponseDto pgreqBriefFindByCreatorResponseDto = Objects.requireNonNull(
			resultResponseEntity.getBody()).getResponse();

		assertThat(pgreqBriefFindByCreatorResponseDto.getStatus()).isEqualTo(PictureGenerateRequestStatus.ASSIGNING);

		Long pgreqId = pgreqBriefFindByCreatorResponseDto.getPictureGenerateRequestId();
		creatorPGREQController.acceptPictureGenerateRequest(roleUpdatedUser.getId(), pgreqId);

		PictureGenerateRequest pgreqCreatorAccepted = pictureGenerateRequestRepository.findById(pgreqId)
			.orElseThrow();
		assertThat(pgreqCreatorAccepted.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.IN_PROGRESS);
		assertThat(pgreqCreatorAccepted.getResponseList().get(0).getStatus()).isEqualTo(
			PictureGenerateResponseStatus.CREATOR_BEFORE_WORK);

		userService.deleteUserSoft(roleUpdatedUser.getId());

		// then
		PictureGenerateRequest pgreqAfterCreatorExit = pictureGenerateRequestRepository.findById(pgreqId)
			.orElseThrow();

		assertThat(pgreqAfterCreatorExit.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.CANCELED);
		assertThat(pgreqAfterCreatorExit.getResponseList().size()).isEqualTo(0);

	}

}