// package com.gt.genti.service;
//
// import static org.assertj.core.api.Assertions.*;
// import static org.junit.jupiter.api.Assertions.*;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Rollback;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.gt.genti.domain.User;
// import com.gt.genti.dto.user.response.UserFindResponseDto;
// import com.gt.genti.dto.user.request.UserInfoUpdateRequestDto;
// import com.gt.genti.repository.UserRepository;
//
// @SpringBootTest
// class UserServiceTest {
//
// 	@Autowired
// 	private UserService userService;
//
// 	@Autowired
// 	private UserRepository userRepository;
// 	@Test
// 	void getUserInfoSuccessTest() {
//
// 		UserFindResponseDto userInfoResponseDto = userService.getUserInfo(1L);
// 		assertNotNull(userInfoResponseDto);
// 		assertThat(userInfoResponseDto.getId()).isEqualTo(1L);
// 		assertThat(userInfoResponseDto.getUsername()).isEqualTo("어드민테스트1_이름");
// 		assertThat(userInfoResponseDto.getPictureId()).isEqualTo(5L);
// 		assertThat(userInfoResponseDto.getUrl()).isEqualTo("url5");
// 	}
//
// 	@Test
// 	@Transactional // Transactional 적용시 항상 rollback
// 	void updateUserInfoSuccessTest(){
// 		User beforeUser = userRepository.findById(1L).orElseThrow();
// 		assertThat(beforeUser.getUsername()).isEqualTo("어드민테스트1_이름");
// 		assertThat(beforeUser.getProfilePicture().getPicture().getUrl()).isEqualTo("url5");
//
// 		String afterName = "이름변경테스트";
// 		String afterProfilePictureUrl = "프로필 url 변경 테스트";
//
// 		UserInfoUpdateRequestDto userInfoUpdateRequestDto = UserInfoUpdateRequestDto.builder()
// 			.userName(afterName)
// 			.profilePictureUrl(afterProfilePictureUrl)
// 			.build();
//
// 		UserFindResponseDto userInfoResponseDto = userService.updateUserInfo(1L, userInfoUpdateRequestDto);
//
// 		assertThat(userInfoResponseDto.getId()).isEqualTo(1L);
// 		assertThat(userInfoResponseDto.getUsername()).isEqualTo(afterName);
// 		assertThat(userInfoResponseDto.getUrl()).isEqualTo(afterProfilePictureUrl);
// 	}
// }