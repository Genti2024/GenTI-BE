package com.gt.genti;

import static com.gt.genti.TestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.UserService;

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
			.posePicture(new CommonPictureKeyUpdateRequestDto(savedPicturePose.getKey()))
			.cameraAngle(CameraAngle.HIGH)
			.pictureRatio(PictureRatio.RATIO_GARO)
			.shotCoverage(ShotCoverage.UPPER_BODY)
			.facePictureList(
				savedPictureUserfaceList.stream().map(d -> new CommonPictureKeyUpdateRequestDto(d.getKey())).toList())
			.build();

		// when
		PictureGenerateRequest createdPGREQ = pictureGenerateRequestService.createPGREQ(
			requester.getId(), req.toCommand());

		// then
		Creator adminCreator = adminService.getAdminCreator();
		assertNotNull(createdPGREQ.getPromptAdvanced());
		assertThat(createdPGREQ.getCreator().getId()).isEqualTo(adminCreator.getId());
		assertThat(createdPGREQ.getPictureGenerateRequestStatus()).isEqualTo(
			PictureGenerateRequestStatus.MATCH_TO_ADMIN);
	}

}
