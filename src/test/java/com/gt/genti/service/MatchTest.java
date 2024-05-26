package com.gt.genti.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gt.genti.adapter.in.web.AdminPictureGenerateWorkController;
import com.gt.genti.application.service.PictureGenerateRequestService;
import com.gt.genti.application.service.RequestMatchService;
import com.gt.genti.application.service.UserService;
import com.gt.genti.command.CreatePicturePoseCommand;
import com.gt.genti.command.CreatePictureUserFaceCommand;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.dto.ChangeUserRoleDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.external.discord.controller.DiscordController;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.UserRepository;
import com.gt.genti.service.config.TestConfig;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
public class MatchTest {
	//@InjectMocks
	//RequestMatchService requestMatchService;
	@Autowired
	PictureGenerateRequestService pictureGenerateRequestService;

	@Autowired
	PictureService pictureService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	AdminService adminService;

	@Autowired
	PictureGenerateRequestRepository pictureGenerateRequestRepository;

	@Mock
	DiscordController discordController;

	@Test
	public void oneRequestWithAdminStrategy() {
		// given
		User requester = getTestUser();
		User savedUser = userRepository.save(requester);

		User adminUser = getTestAdminUser();
		User savedAdmin = userRepository.save(adminUser);
		// admin으로 userrole 변경시 admin creator 생성됨
		userService.updateUserRole(savedAdmin.getId(), ChangeUserRoleDto.builder().userRole(UserRole.ADMIN).build());
		Creator foundCreator = adminService.getAdminCreator();
		assertThat(foundCreator).isNotNull();

		CreatePicturePoseCommand command = getCreatePicturePoseCommand(savedUser);
		PicturePose savedPicturePose = pictureService.updatePicture(command);

		List<CreatePictureUserFaceCommand> commandList = getPictureUserFaceCommandList(
			savedUser);
		List<PictureUserFace> savedPictureUserfaceList = pictureService.updatePictureUserFaceAll(commandList);

		PictureGenerateRequestRequestDto req = PictureGenerateRequestRequestDto.builder()
			.prompt("벚꽃여자요")
			.posePictureUrl(savedPicturePose.getUrl())
			.cameraAngle(CameraAngle.ABOVE)
			.shotCoverage(ShotCoverage.FACE)
			.facePictureUrlList(savedPictureUserfaceList.stream().map(PictureUserFace::getUrl).toList())
			.build();

		// when
		PictureGenerateRequest savedPictureGenerateRequest = pictureGenerateRequestService.createPictureGenerateRequest(
			requester.getId(), req);

		// then
		Creator adminCreator = adminService.getAdminCreator();
		// verify(discordController).sendToAdminChannel(anyString());
		assertThat(savedPictureGenerateRequest.getCreator()).isEqualTo(adminCreator);
		assertThat(savedPictureGenerateRequest.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.MATCH_TO_ADMIN);
	}

	private User getTestAdminUser() {
		return User.builder()
			.userRole(UserRole.ADMIN)
			.roles(UserRole.ADMIN.getStringValue())
			.userStatus(UserStatus.ACTIVATED)
			.lastLoginSocialPlatform(OauthType.GOOGLE)
			.username("username")
			.build();
	}

	private static CreatePicturePoseCommand getCreatePicturePoseCommand(User savedUser) {
		CreatePicturePoseCommand command = CreatePicturePoseCommand.builder()
			.url("/test/picturepose")
			.user(savedUser)
			.build();
		return command;
	}

	@NotNull
	private static List<CreatePictureUserFaceCommand> getPictureUserFaceCommandList(User savedUser) {
		CreatePictureUserFaceCommand command1 = CreatePictureUserFaceCommand.builder()
			.url("/test/pictureuserface1")
			.user(savedUser)
			.build();
		CreatePictureUserFaceCommand command2 = CreatePictureUserFaceCommand.builder()
			.url("/test/pictureuserface2")
			.user(savedUser)
			.build();
		CreatePictureUserFaceCommand command3 = CreatePictureUserFaceCommand.builder()
			.url("/test/pictureuserface3")
			.user(savedUser)
			.build();

		List<CreatePictureUserFaceCommand> commandList = List.of(command1, command2, command3);
		return commandList;
	}

	private static User getTestUser() {
		return User.builder()
			.userStatus(UserStatus.ACTIVATED)
			.userRole(UserRole.USER)
			.creator(null)
			.deletedAt(null)
			.email("test@test.com")
			.emailVerified(false)
			.introduction("소개")
			.roles(UserRole.USER.getStringValue())
			.loginId(null)
			.password(null)
			.pictureProfile(null)
			.lastLoginSocialPlatform(OauthType.GOOGLE)
			.nickname("nickname")
			.pictureUserFaceList(null)
			.username("username")
			.build();
	}
}
