package com.gt.genti;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gt.genti.common.AdminService;
import com.gt.genti.config.TestConfig;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.command.CreatePicturePoseCommand;
import com.gt.genti.picture.command.CreatePictureUserFaceCommand;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.pose.model.PicturePose;
import com.gt.genti.picture.service.PictureService;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegeneraterequest.service.PictureGenerateRequestService;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.model.UserStatus;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserService;

import jakarta.validation.constraints.NotNull;

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

	@BeforeAll
	public static void setup() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

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
			.posePicture(new CommonPictureKeyUpdateRequestDto(savedPicturePose.getKey()))
			.cameraAngle(CameraAngle.HIGH)
			.pictureRatio(PictureRatio.RATIO_3_2)
			.shotCoverage(ShotCoverage.UPPER_BODY)
			.facePictureList(
				savedPictureUserfaceList.stream().map(d -> new CommonPictureKeyUpdateRequestDto(d.getKey())).toList())
			.build();

		// when
		PictureGenerateRequest createdPGREQ = pictureGenerateRequestService.createPGREQ(
			requester.getId(), req.toCommand());

		// then
		Creator adminCreator = adminService.getAdminCreator();
		// verify(discordController).sendToAdminChannel(anyString());
		assertNotNull(createdPGREQ.getPromptAdvanced());
		assertThat(createdPGREQ.getCreator().getId()).isEqualTo(adminCreator.getId());
		assertThat(createdPGREQ.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.MATCH_TO_ADMIN);
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

	private User getTestAdminUser() {
		return User.test()
			.socialId(UUID.randomUUID().toString())
			.imageUrl("imageUrl")
			.userStatus(UserStatus.ACTIVATED)
			.userRole(UserRole.ADMIN)
			.deletedAt(null)
			.email("admin@admin.com")
			.lastLoginOauthPlatform(OauthPlatform.GOOGLE)
			.nickname("nickname")
			.username("username")
			.lastLoginDate(LocalDateTime.now())
			.build();
	}

	private static User getTestUser() {
		return User.test()
			.socialId(UUID.randomUUID().toString())
			.imageUrl("imageUrl")
			.userStatus(UserStatus.ACTIVATED)
			.userRole(UserRole.USER)
			.deletedAt(null)
			.email("test@test.com")
			.lastLoginOauthPlatform(OauthPlatform.GOOGLE)
			.nickname("nickname")
			.username("username")
			.lastLoginDate(LocalDateTime.now())
			.build();
	}
}
