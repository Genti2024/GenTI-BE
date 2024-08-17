package com.gt.genti;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gt.genti.aws.FileType;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.command.CreatePicturePoseCommand;
import com.gt.genti.picture.command.CreatePictureUserFaceCommand;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.report.dto.request.ReportCreateRequestDto;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.model.UserStatus;

import jakarta.validation.constraints.NotNull;

public class TestUtils {
	public static User getTestUser() {
		return User.test()
			.oauthImageUrl("url")
			.socialId(UUID.randomUUID().toString())
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

	public static User getTestCreatorUser(){
		return User.test()
			.oauthImageUrl("url")
			.socialId(UUID.randomUUID().toString())
			.userStatus(UserStatus.ACTIVATED)
			.userRole(UserRole.USER)
			.deletedAt(null)
			.email("creator@creator.com")
			.lastLoginOauthPlatform(OauthPlatform.GOOGLE)
			.nickname("creatornickname")
			.username("creatorname")
			.lastLoginDate(LocalDateTime.now())
			.build();
	}

	public static User getTestAdminUser() {
		return User.test()
			.oauthImageUrl("url")
			.socialId(UUID.randomUUID().toString())
			.userStatus(UserStatus.ACTIVATED)
			.userRole(UserRole.ADMIN)
			.deletedAt(null)
			.email("test@test.com")
			.lastLoginOauthPlatform(OauthPlatform.GOOGLE)
			.nickname("nickname")
			.username("username")
			.lastLoginDate(LocalDateTime.now())
			.build();
	}

	@NotNull
	public static List<CreatePictureUserFaceCommand> getPictureUserFaceCommandList(User savedUser) {
		CreatePictureUserFaceCommand command1 = CreatePictureUserFaceCommand.builder()
			.key("/USER_UPLOADED_IMAGE/pictureuploaderface1")
			.uploader(savedUser)
			.build();
		CreatePictureUserFaceCommand command2 = CreatePictureUserFaceCommand.builder()
			.key("/USER_UPLOADED_IMAGE/pictureuploaderface2")
			.uploader(savedUser)
			.build();
		CreatePictureUserFaceCommand command3 = CreatePictureUserFaceCommand.builder()
			.key("/USER_UPLOADED_IMAGE/pictureuploaderface3")
			.uploader(savedUser)
			.build();

		return List.of(command1, command2, command3);
	}

	public static CreatePicturePoseCommand getCreatePicturePoseCommand(User savedUser) {
		return CreatePicturePoseCommand
			.builder()
			.key("/test/picturepose")
			.uploader(savedUser)
			.build();
	}

	public static UserRoleUpdateRequestDto getUserRoleUpdateRequestDto(UserRole userRole) {
		return UserRoleUpdateRequestDto.builder()
			.userRole(userRole)
			.build();
	}

	public static PGREQSaveRequestDto getPGREQSaveRequestDto() {
		return PGREQSaveRequestDto.builder()
			.pictureRatio(PictureRatio.RATIO_SERO)
			.shotCoverage(ShotCoverage.KNEE_SHOT)
			.posePicture(getCommonPictureKeyUpdateRequestDto(FileType.USER_UPLOADED_IMAGE))
			.facePictureList(get3CommonPictureKeyUpdateRequestDtos(FileType.USER_UPLOADED_IMAGE))
			.cameraAngle(CameraAngle.HIGH)
			.prompt("카페에서 흰색 남방을 입은 백엔드 개발자가 지친 상태로 작업하는 모습")
			.build();
	}

	public static CommonPictureKeyUpdateRequestDto getCommonPictureKeyUpdateRequestDto(FileType fileType) {
		return new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test");
	}

	public static List<CommonPictureKeyUpdateRequestDto> get3CommonPictureKeyUpdateRequestDtos(FileType fileType){
		return List.of(new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test1"),
			new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test2"),
			new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test3"));
	}

	public static ReportCreateRequestDto getReportCreateRequestDto(Long pgresId) {
		return new ReportCreateRequestDto(pgresId,"별로야");
	}
}
