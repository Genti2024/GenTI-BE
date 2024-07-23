package com.gt.genti;

import static com.gt.genti.TestUtils.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gt.genti.config.TestConfig;
import com.gt.genti.matchingstrategy.model.RequestMatchStrategy;
import com.gt.genti.picturegeneraterequest.controller.CreatorPGREQController;
import com.gt.genti.picturegeneraterequest.controller.UserPGREQController;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegeneraterequest.service.RequestMatchService;
import com.gt.genti.picturegeneraterequest.service.mapper.PictureGenerateRequestStatusForUser;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.report.service.ReportService;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserService;

@ActiveProfiles({"common", "secret", "test"})
@SpringBootTest(classes = TestConfig.class)
public class CancelledPGREQTest {
	@Autowired
	PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Autowired
	UserPGREQController userPGREQController;

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


	@Test
	void canceledPGREQTest(){
	    // given
		User creatorUser = getTestCreatorUser();
		User savedCreatorUser = userRepository.save(creatorUser);
		userService.updateUserRole(savedCreatorUser.getId(), getUserRoleUpdateRequestDto(UserRole.CREATOR));

		// 요청을 수행할 유저 생성
		User newUser = getTestUser();
		User savedUser = userRepository.save(newUser);

		// 사진생성요청 생성 dto
		PGREQSaveRequestDto pgreqSaveRequestDto = getPGREQSaveRequestDto();

		// 공급자에게 매칭되기위해 매칭전략 변경
		requestMatchService.changeMatchingStrategy(RequestMatchStrategy.CREATOR_ADMIN);
		
		// 사진생성요청 생성
		PictureGenerateRequest pgreq = pictureGenerateRequestUseCase.createPGREQ(savedUser.getId(), pgreqSaveRequestDto.toCommand());

		// 강제 취소처리 
		pgreq.canceled();
		PictureGenerateRequest canceledPGREQ = pictureGenerateRequestRepository.save(pgreq);

		assertThat(canceledPGREQ.getPictureGenerateRequestStatus()).isEqualTo(PictureGenerateRequestStatus.CANCELED);

		// when
		// 현재 사진생성요청의 상태를 조회
		PGREQStatusResponseDto responseDto = userPGREQController.getPendingPGRESStatus(savedUser.getId()).getBody().getResponse();

		// then
		// 취소상태여야한다.
		assertThat(responseDto.getPictureGenerateRequestId()).isEqualTo(pgreq.getId());
		assertThat(responseDto.getStatus()).isEqualTo(PictureGenerateRequestStatusForUser.CANCELED);
		assertThat(responseDto.getPictureGenerateResponse()).isNull();

		// when
		// 사진생성요청이 취소되었음을 유저가 인지했다고 알리는 api 호출
		Boolean isSuccess = userPGREQController.confirmCanceledPGREQ(savedUser.getId(), responseDto.getPictureGenerateRequestId())
			.getBody().getResponse();
		assertThat(isSuccess).isTrue();
		// 현재 사진생성요청의 상태를 다시 조회
		PGREQStatusResponseDto responseDtoAfterConfirmCanceled = userPGREQController.getPendingPGRESStatus(savedUser.getId()).getBody().getResponse();

		// then
		// 새로운 요청이 가능해야한다.
		assertThat(responseDtoAfterConfirmCanceled.getPictureGenerateRequestId()).isNull();
		assertThat(responseDtoAfterConfirmCanceled.getStatus()).isEqualTo(PictureGenerateRequestStatusForUser.NEW_REQUEST_AVAILABLE);
		assertThat(responseDtoAfterConfirmCanceled.getPictureGenerateResponse()).isNull();

	}
}
