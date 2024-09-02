package com.gt.genti;

import static com.gt.genti.TestUtils.Domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gt.genti.aws.FileType;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.command.CreatePicturePoseCommand;
import com.gt.genti.picture.command.CreatePictureUserFaceCommand;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.pose.model.PicturePose;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.report.dto.request.ReportCreateRequestDto;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.model.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class TestUtils {

	@Getter
	public static class PictureGenerateRequestSetup {
		private User user;
		private PictureGenerateRequest pictureGenerateRequest;
		private PicturePose picturePose;
		private List<PictureUserFace> pictureUserFaceList = new ArrayList<>();

		public static PictureGenerateRequestSetup get(Creator adminCreator) {
			PictureGenerateRequestSetup result = new PictureGenerateRequestSetup();
			result.user = createUser(UserRole.USER);
			result.picturePose = createPicturePose(result.user);
			result.pictureUserFaceList = createPictureUserFace3(result.user);
			result.pictureGenerateRequest = createPictureGenerateRequest(result.user, result.picturePose,
				result.pictureUserFaceList);
			result.pictureGenerateRequest.assignToAdmin(adminCreator);
			result.user.getPicturePoseList().add(result.picturePose);
			result.user.getPictureUserFaceList().addAll(result.pictureUserFaceList);
			result.user.getPictureGenerateRequestList().add(result.pictureGenerateRequest);
			return result;
		}
	}

	public static class Domain {

		public static User createUser(UserRole userRole) {
			return User.test()
				.oauthImageUrl("url")
				.socialId(UUID.randomUUID().toString())
				.userStatus(UserStatus.ACTIVATED)
				.userRole(userRole)
				.deletedAt(null)
				.email("test@test.com")
				.lastLoginOauthPlatform(OauthPlatform.GOOGLE)
				.nickname("nickname")
				.username("username")
				.lastLoginDate(LocalDateTime.now())
				.build();
		}

		public static PictureGenerateRequest createPictureGenerateRequest(User user, PicturePose picturePose,
			List<PictureUserFace> pictureUserFaceList) {
			return PictureGenerateRequest.builder()
				.requester(user)
				.cameraAngle(CameraAngle.HIGH)
				.picturePose(picturePose)
				.pictureRatio(PictureRatio.RATIO_GARO)
				.prompt("prompt")
				.promptAdvanced("promptAdvanced")
				.shotCoverage(ShotCoverage.KNEE_SHOT)
				.userFacePictureList(pictureUserFaceList)
				.build();
		}

		public static List<PictureUserFace> createPictureUserFace3(User user) {
			List<PictureUserFace> pictureUserFaceList = new ArrayList<>();
			pictureUserFaceList.add(createPictureUserFace(user));
			pictureUserFaceList.add(createPictureUserFace(user));
			pictureUserFaceList.add(createPictureUserFace(user));
			return pictureUserFaceList;
		}

		public static PictureUserFace createPictureUserFace(User user) {
			return PictureUserFace.builder().key(UUID.randomUUID().toString()).user(user).build();
		}

		public static PicturePose createPicturePose(User user) {
			return new PicturePose(UUID.randomUUID().toString(), user);
		}

		public static Creator createCreator(User adminUser) {
			return new Creator(adminUser);
		}
	}

	public static class Command {
		public static PGREQSaveCommand getPGREQSaveCommand() {
			return PGREQSaveCommand.builder()
				.cameraAngle(CameraAngle.ANY)
				.facePictureKeyList(List.of("test", "test", "test"))
				.pictureRatio(PictureRatio.RATIO_GARO)
				.prompt("prompt")
				.shotCoverage(ShotCoverage.KNEE_SHOT)
				.build();
		}
	}

	public static class Dto {
		public static UserRoleUpdateRequestDto getUserRoleUpdateRequestDto(UserRole userRole) {
			return UserRoleUpdateRequestDto.builder().userRole(userRole).build();
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

		public static List<CommonPictureKeyUpdateRequestDto> get3CommonPictureKeyUpdateRequestDtos(FileType fileType) {
			return List.of(new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test1"),
				new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test2"),
				new CommonPictureKeyUpdateRequestDto(fileType.getStringValue() + "/test3"));
		}

		public static ReportCreateRequestDto getReportCreateRequestDto(Long pgresId) {
			return new ReportCreateRequestDto(pgresId, "별로야");
		}
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
		return CreatePicturePoseCommand.builder().key("/test/picturepose").uploader(savedUser).build();
	}

}

