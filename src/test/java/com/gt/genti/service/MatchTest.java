package com.gt.genti.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gt.genti.application.service.PictureGenerateRequestService;
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
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.dto.admin.request.UserRoleUpdateRequestDto;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.UserRepository;
import com.gt.genti.service.config.TestConfig;

@ActiveProfiles({"test", "common", "secret"})
@SpringBootTest(classes = TestConfig.class)
public class MatchTest {
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

	@Test
	public void oneRequestWithAdminStrategy() {
		// given
		User requester = getTestUser();
		User savedUser = userRepository.save(requester);

		User adminUser = getTestAdminUser();
		User savedAdmin = userRepository.save(adminUser);
		// admin으로 uploaderrole 변경시 admin creator 생성됨
		userService.updateUserRole(savedAdmin.getId(),
			UserRoleUpdateRequestDto.builder().userRole(UserRole.ADMIN).build());

		CreatePicturePoseCommand command = getCreatePicturePoseCommand(savedUser);
		PicturePose savedPicturePose = pictureService.updatePicture(command);

		List<CreatePictureUserFaceCommand> commandList = getPictureUserFaceCommandList(
			savedUser);
		List<PictureUserFace> savedPictureUserfaceList = pictureService.updatePictureUserFaceAll(commandList);

		PGREQSaveRequestDto req = PGREQSaveRequestDto.builder()
			.prompt("밤에 한강공원에서 벤치에 앉은 사진이요")
			.posePictureKey(savedPicturePose.getKey())
			.cameraAngle(CameraAngle.HIGH.getStringValue())
			.pictureRatio(PictureRatio.RATIO_3_2.getStringValue())
			.shotCoverage(ShotCoverage.UPPER_BODY.getStringValue())
			.facePictureKeyList(savedPictureUserfaceList.stream().map(PictureUserFace::getKey).toList())
			.build();

		// when
		PictureGenerateRequest createdPGREQ = pictureGenerateRequestService.createPictureGenerateRequest(
			requester, req.toCommand());

		// then
		Creator adminCreator = adminService.getAdminCreator();
		// verify(discordController).sendToAdminChannel(anyString());
		assertNotNull(createdPGREQ.getPromptAdvanced());
		assertThat(createdPGREQ.getCreator().getId()).isEqualTo(adminCreator.getId());
		assertThat(createdPGREQ.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.MATCH_TO_ADMIN);
	}

	private User getTestAdminUser() {
		return User.builder()
			.userRole(UserRole.ADMIN)
			.userStatus(UserStatus.ACTIVATED)
			.lastLoginSocialPlatform(OauthType.GOOGLE)
			.username("adminusername")
			.lastLoginDate(LocalDateTime.now())
			.build();
	}

	private static CreatePicturePoseCommand getCreatePicturePoseCommand(User savedUser) {
		return CreatePicturePoseCommand
			.builder()
			.key("/test/picturepose")
			.uploader(savedUser)
			.build();
	}

	@NotNull
	private static List<CreatePictureUserFaceCommand> getPictureUserFaceCommandList(User savedUser) {
		CreatePictureUserFaceCommand command1 = CreatePictureUserFaceCommand.builder()
			.key("/test/pictureuploaderface1")
			.uploader(savedUser)
			.build();
		CreatePictureUserFaceCommand command2 = CreatePictureUserFaceCommand.builder()
			.key("/test/pictureuploaderface2")
			.uploader(savedUser)
			.build();
		CreatePictureUserFaceCommand command3 = CreatePictureUserFaceCommand.builder()
			.key("/test/pictureuploaderface3")
			.uploader(savedUser)
			.build();

		return List.of(command1, command2, command3);
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
			.loginId(null)
			.password(null)
			.pictureProfileList(null)
			.lastLoginSocialPlatform(OauthType.GOOGLE)
			.nickname("nickname")
			.pictureUserFaceList(null)
			.username("username")
			.lastLoginDate(LocalDateTime.now())
			.build();
	}
}
