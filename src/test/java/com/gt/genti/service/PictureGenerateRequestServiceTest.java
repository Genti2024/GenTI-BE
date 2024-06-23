// package com.gt.genti.service;
//
// import static org.assertj.core.api.Assertions.*;
// import static org.mockito.BDDMockito.*;
//
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.gt.genti.domain.PictureGenerateRequest;
// import com.gt.genti.domain.PosePicture;
// import com.gt.genti.domain.User;
// import com.gt.genti.domain.UserFacePicture;
// import com.gt.genti.domain.enums.CameraAngle;
// import com.gt.genti.domain.enums.RequestStatus;
// import com.gt.genti.domain.enums.ShotCoverage;
// import com.gt.genti.dto.admin.response.PGREQDetailFindResponseDto;
// import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
// import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
// import com.gt.genti.repository.PictureGenerateRequestRepository;
// import com.gt.genti.repository.PosePictureRepository;
// import com.gt.genti.repository.UserRepository;
//
// @SpringBootTest
// class PictureGenerateRequestServiceTest {
//
// 	@Autowired
// 	private PictureGenerateRequestService pictureGenerateRequestService;
//
// 	@Autowired
// 	private PictureGenerateRequestRepository pictureGenerateRequestRepository;
//
// 	@Autowired
// 	private UserRepository userRepository;
//
// 	@Mock
// 	private PosePictureRepository posePictureRepository;
//
// 	@Test
// 	@Transactional
// 	void getMyActivePictureGenerateRequestsSuccess() {
//
// 		// given
// 		User user = userRepository.findById(3L).orElseThrow();
// 		List<String> givenFacePictureUrlList = user.getUserFacePictureList().stream().map(
// 			UserFacePicture::getUrl).toList();
//
// 		// when
// 		List<PGREQDetailFindResponseDto> pictureGenerateRequestDetailResponseDtoList = pictureGenerateRequestService.getMyActivePictureGenerateRequest(
// 			3L);
// 		PGREQDetailFindResponseDto recentDto = pictureGenerateRequestDetailResponseDtoList.get(0);
//
// 		// then
// 		assertThat(recentDto.getId()).isEqualTo(2L);
// 		assertThat(recentDto.getRequesterId()).isEqualTo(3L);
// 		assertThat(recentDto.getRequestStatus()).isEqualTo(RequestStatus.IN_PROGRESS);
// 		assertThat(recentDto.getPosePictureId()).isEqualTo(3L);
// 		assertThat(recentDto.getPosePictureUrl()).isEqualTo("pose_picture_url3");
// 		assertThat(recentDto.getPrompt()).isEqualTo("prompt_test_2");
// 		assertThat(recentDto.getFacePictureUrlList()).isEqualTo(givenFacePictureUrlList);
// 		assertThat(recentDto.getCameraAngle()).isEqualTo(CameraAngle.EYE_LEVEL);
// 		assertThat(recentDto.getShotCoverage()).isEqualTo(ShotCoverage.UPPER_BODY);
//
// 	}
//
// 	@Test
// 	@Transactional
// 	@DisplayName("사진생성요청을 이미 업로드 && DB에 저장되어 있지 않은 포즈 참고사진으로 생성")
// 	void createPictureGenerateRequestSuccessWithExistPosePictureTest() {
//
// 		// given
// 		String randomPrompt = UUID.randomUUID().toString();
// 		String posePictureUrl = UUID.randomUUID().toString();
// 		PGREQSaveRequestDto reqDto = PGREQSaveRequestDto.builder()
// 			.cameraAngle(CameraAngle.EYE_LEVEL.getStringValue())
// 			.posePictureUrl(posePictureUrl)
// 			.prompt(randomPrompt)
// 			.shotCoverage(ShotCoverage.FULL_BODY.getStringValue())
// 			.build();
//
// 		User user = userRepository.findById(4L).orElseThrow();
//
// 		// when
// 		pictureGenerateRequestService.createPictureGenerateRequest(user, reqDto);
//
// 		// then
// 		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestRepository.findByRequestStatusIsActiveAndUserId_JPQL(
// 			4L).get(0);
// 		assertThat(findPictureGenerateRequest.getCreator()).isNull();
// 		assertThat(findPictureGenerateRequest.getRequester()).isEqualTo(user);
// 		assertThat(findPictureGenerateRequest.getPosePicture().getUrl()).isEqualTo(posePictureUrl);
// 		assertThat(findPictureGenerateRequest.getRequestStatus()).isEqualTo(RequestStatus.BEFORE_WORK);
// 		assertThat(findPictureGenerateRequest.getCreatedAt()).isNotNull();
// 		assertThat(findPictureGenerateRequest.getModifiedAt()).isNotNull();
// 		assertThat(findPictureGenerateRequest.getCameraAngle()).isEqualTo(CameraAngle.EYE_LEVEL);
// 		assertThat(findPictureGenerateRequest.getShotCoverage()).isEqualTo(ShotCoverage.FULL_BODY);
// 		assertThat(findPictureGenerateRequest.getResponseList()).isNull();
//
// 		verify(posePictureRepository).save(any());
// 	}
//
// 	@Test
// 	@Transactional
// 	@DisplayName("사진생성요청을 이미 업로드 && DB에 저장되어있는 포즈 참고사진으로 생성")
// 	void createPictureGenerateRequestSuccessWithNotExistPosePictureTest() {
//
// 		// given
// 		String randomPrompt = UUID.randomUUID().toString();
// 		String randomUrl = UUID.randomUUID().toString();
// 		given(posePictureRepository.findByUrl(randomUrl)).willReturn(
// 			Optional.of(PosePicture.builder().id(1L).url(randomUrl).build()));
//
// 		PGREQSaveRequestDto reqDto = PGREQSaveRequestDto.builder()
// 			.cameraAngle(CameraAngle.EYE_LEVEL.getStringValue())
// 			.posePictureUrl(randomUrl)
// 			.prompt(randomPrompt)
// 			.shotCoverage(ShotCoverage.FULL_BODY.getStringValue())
// 			.build();
//
// 		User user = userRepository.findById(4L).orElseThrow();
//
// 		// when
// 		assertThat(pictureGenerateRequestService.createPictureGenerateRequest(user, reqDto)).isTrue();
//
// 		// then
// 		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestRepository.findByRequestStatusIsActiveAndUserId_JPQL(
// 			4L).get(0);
// 		assertThat(findPictureGenerateRequest.getCreator()).isNull();
// 		assertThat(findPictureGenerateRequest.getRequester()).isEqualTo(user);
// 		assertThat(findPictureGenerateRequest.getPosePicture().getUrl()).isEqualTo(randomUrl);
// 		assertThat(findPictureGenerateRequest.getRequestStatus()).isEqualTo(RequestStatus.BEFORE_WORK);
// 		assertThat(findPictureGenerateRequest.getCreatedAt()).isNotNull();
// 		assertThat(findPictureGenerateRequest.getModifiedAt()).isNotNull();
// 		assertThat(findPictureGenerateRequest.getCameraAngle()).isEqualTo(CameraAngle.EYE_LEVEL);
// 		assertThat(findPictureGenerateRequest.getShotCoverage()).isEqualTo(ShotCoverage.FULL_BODY);
// 		assertThat(findPictureGenerateRequest.getResponseList()).isNull();
//
// 		verify(posePictureRepository, never()).save(any());
// 	}
//
// 	@Test
// 	@Transactional
// 	void modifyPictureGenerateRequestSuccess() {
//
// 		// given
// 		PictureGenerateRequest before = pictureGenerateRequestRepository.findById(1L).orElseThrow();
//
// 		assertThat(before.getPrompt()).isEqualTo("prompt_test");
// 		assertThat(before.getShotCoverage()).isEqualTo(ShotCoverage.FACE);
// 		assertThat(before.getRequestStatus()).isEqualTo(RequestStatus.BEFORE_WORK);
// 		assertThat(before.getCameraAngle()).isEqualTo(CameraAngle.ABOVE);
// 		assertThat(before.getPosePicture().getUrl()).isEqualTo("pose_picture_url1");
//
// 		String randomPosePictureUrl = UUID.randomUUID().toString();
// 		String randomPrompt = UUID.randomUUID().toString();
//
// 		PGREQUpdateRequestDto modifyDto = PGREQUpdateRequestDto.builder()
// 			.id(1L)
// 			.posePictureUrl(randomPosePictureUrl)
// 			.shotCoverage(ShotCoverage.UPPER_BODY.getStringValue())
// 			.cameraAngle(CameraAngle.BELOW.getStringValue())
// 			.prompt(randomPrompt).build();
//
// 		assertThat(pictureGenerateRequestService.modifyPictureGenerateRequest(3L, modifyDto)).isTrue();
//
// 		PictureGenerateRequest after = pictureGenerateRequestRepository.findById(1L).orElseThrow();
//
// 		assertThat(after.getPrompt()).isEqualTo(randomPrompt);
// 		assertThat(after.getShotCoverage()).isEqualTo(ShotCoverage.UPPER_BODY);
// 		assertThat(after.getCameraAngle()).isEqualTo(CameraAngle.BELOW);
//
//
//
//
// 		assertThat(after.getPosePicture().getUrl()).isEqualTo(randomPosePictureUrl);
//
// 		assertThat(after.getPosePicture().getId()).isNotEqualTo(before.getPosePicture().getId());
//
// 	}
// }