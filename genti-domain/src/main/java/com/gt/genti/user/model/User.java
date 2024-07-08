package com.gt.genti.user.model;

import static com.gt.genti.constants.UserConstants.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.common.converter.OauthPlatformConverterIgnoreCase;
import com.gt.genti.common.converter.SexConverter;
import com.gt.genti.common.converter.UserRoleConverter;
import com.gt.genti.common.converter.UserStatusConverter;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.user.UserSerializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users") // h2 예약어 해결법 못찾음
@Entity
@Getter
@JsonSerialize(using = UserSerializer.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, unique = true)
	String socialId;

	@Column(nullable = false, length = 512)
	String imageUrl;

	// PictureProfile는 완전히 user에 종속되어있다
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "profile_picture_id")
	List<PictureProfile> pictureProfileList;

	// PictureUserFace는 완전히 user에 종속
	@OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL, orphanRemoval = true)
	List<PictureUserFace> pictureUserFaceList;

	@Column(name = "email")
	String email;

	@Column(name = "sex", nullable = false)
	@Convert(converter = SexConverter.class)
	Sex sex;

	@Column(name = "introduction", nullable = false)
	String introduction;

	@Column(name = "username", nullable = false)
	String username;

	@Column(name = "nickname")
	String nickname;

	@Column(name = "user_status", nullable = false)
	@Convert(converter = UserStatusConverter.class)
	UserStatus userStatus;

	@Column(name = "email_verified")
	Boolean emailVerified;

	@Column(name = "login_id")
	String loginId;

	@Column(name = "password")
	String password;

	// user hard delete 시에 같이 삭제
	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	Creator creator;

	@Column(name = "user_role", nullable = false)
	@Convert(converter = UserRoleConverter.class)
	UserRole userRole;

	@Column(name = "last_login_oauth_platform")
	@Convert(converter = OauthPlatformConverterIgnoreCase.class)
	OauthPlatform lastLoginOauthPlatform;

	@Column(name = "deleted_at")
	LocalDateTime deletedAt;

	@Column(name = "last_login_date", nullable = false)
	LocalDateTime lastLoginDate;

	// user hard delete시 deposit도 삭제
	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
	private Deposit deposit;

	@Column(name = "request_task_count", nullable = false)
	Integer requestTaskCount;

	@Column(name = "birth_date")
	LocalDate birthDate;

	@PrePersist
	public void prePersist() {
		if (this.userStatus == null) {
			this.userStatus = UserStatus.ACTIVATED;  // 기본값 설정
		}
		if (this.sex == null) {
			this.sex = Sex.NONE;
		}
		if (this.introduction == null) {
			this.introduction = "";
		}

		if (this.requestTaskCount == null) {
			this.requestTaskCount = 0;
		}
	}

	@Builder(builderMethodName = "builderWithSignIn", builderClassName = "SignInUser")
	public static User of(String socialId, OauthPlatform oauthPlatform, String username, String imageUrl,
		String email) {
		return base()
			.socialId(socialId)
			.lastLoginOauthPlatform(oauthPlatform)
			.lastLoginDate(LocalDateTime.now())
			.userRole(UserRole.OAUTH_FIRST_JOIN)
			.username(username)
			.imageUrl(imageUrl)
			.email(email)
			.build();
	}

	@Builder(builderMethodName = "test", builderClassName = "test")
	public static User of(String socialId, String imageUrl, UserStatus userStatus, UserRole userRole, LocalDateTime deletedAt, String email, OauthPlatform lastLoginOauthPlatform, String nickname, String username, LocalDateTime lastLoginDate) {
		return base()
			.socialId(socialId)
			.imageUrl(imageUrl)
			.lastLoginOauthPlatform(lastLoginOauthPlatform)
			.lastLoginDate(lastLoginDate)
			.userRole(userRole)
			.username(username)
			.nickname(nickname)
			.userStatus(userStatus)
			.email(email)
			.deletedAt(deletedAt)
			.build();
	}

	public void updateName(String username) {
		this.username = username;
	}

	public void addProfilePicture(PictureProfile pictureProfile) {
		this.getPictureProfileList().add(pictureProfile);
	}

	public void softDelete() {
		this.userStatus = UserStatus.DEACTIVATED;
		this.deletedAt = LocalDateTime.now();
	}

	public void restore() {
		if (Period.between(this.deletedAt.toLocalDate(), LocalDate.now()).getMonths() >= 1) {
			throw ExpectedException.withLogging(ResponseCode.CannotRestoreUser, this.getId().toString());
		}
		this.userStatus = UserStatus.ACTIVATED;
	}

	public Boolean isActivate() {
		return this.userStatus == UserStatus.ACTIVATED;
	}

	public void updateStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public void updateUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public void login() {
		this.lastLoginDate = LocalDateTime.now();
	}

	@Builder(builderMethodName = "base", builderClassName = "base")
	private User(Long id, String socialId, String imageUrl, List<PictureProfile> pictureProfileList,
		List<PictureUserFace> pictureUserFaceList, String email, Sex sex, String introduction, String username,
		String nickname, UserStatus userStatus, Boolean emailVerified, String loginId, String password, Creator creator,
		UserRole userRole, OauthPlatform lastLoginOauthPlatform, LocalDateTime deletedAt, LocalDateTime lastLoginDate,
		Deposit deposit, Integer requestTaskCount, LocalDate birthDate) {
		this.id = id;
		this.socialId = socialId;
		this.imageUrl = imageUrl;
		this.pictureProfileList = pictureProfileList;
		this.pictureUserFaceList = pictureUserFaceList;
		this.email = email;
		this.sex = sex;
		this.introduction = introduction;
		this.username = username;
		this.nickname = nickname;
		this.userStatus = userStatus;
		this.emailVerified = emailVerified;
		this.loginId = loginId;
		this.password = password;
		this.creator = creator;
		this.userRole = userRole;
		this.lastLoginOauthPlatform = lastLoginOauthPlatform;
		this.deletedAt = deletedAt;
		this.lastLoginDate = lastLoginDate;
		this.deposit = deposit;
		this.requestTaskCount = requestTaskCount;
		this.birthDate = birthDate;
	}

	public void addRequestCount() {
		this.requestTaskCount += 1;
	}

	public void resetDeleteAt() {
		this.deletedAt = null;
	}

	public void setDeleteAt() {
		this.deletedAt = LocalDateTime.now().plusDays(USER_RETENTION_PERIOD);
	}
}
