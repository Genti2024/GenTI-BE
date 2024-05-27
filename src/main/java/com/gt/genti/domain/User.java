package com.gt.genti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.domain.enums.converter.OauthTypeConverterIgnoreCase;
import com.gt.genti.domain.enums.converter.UserRoleConverter;
import com.gt.genti.domain.enums.converter.UserStatusConverter;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.OAuthAttributes;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	// PictureProfile는 완전히 user에 종속되어있다
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "profile_picture_id")
	List<PictureProfile> pictureProfileList;

	// PictureUserFace는 완전히 user에 종속
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	List<PictureUserFace> pictureUserFaceList;

	@Column(name = "email")
	String email;

	@Column(name = "introduction")
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

	@Column(name = "userRole", nullable = false)
	@Convert(converter = UserRoleConverter.class)
	UserRole userRole;

	@Column(name = "roles", nullable = false)
	String roles;

	@Column(name = "last_login_social_platform")
	@Convert(converter = OauthTypeConverterIgnoreCase.class)
	OauthType lastLoginSocialPlatform;

	@Column(name = "deleted_at")
	LocalDateTime deletedAt;

	// user hard delete시 deposit도 삭제
	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
	private Deposit deposit;

	public static User createPrincipalOnlyUser(Long id) {
		return new User(id);
	}

	public static User createNewSocialUser(String email, String username, OauthType oauthType) {
		//TODO 최초가입자 이름 랜덤 생성
		// edited at 2024-04-25
		// author
		return new User(email, username, "최초로그인시닉네임", oauthType, UserRole.USER);
	}

	/**
	 * 소셜유저로그인
	 */
	private User(String email, String username, String nickname, OauthType oauthType, UserRole userRole) {
		this.email = email;
		this.username = username;
		this.nickname = nickname;
		this.userRole = userRole;
		this.roles = userRole.getStringValue();
		this.lastLoginSocialPlatform = oauthType;
		this.userStatus = UserStatus.ACTIVATED;
	}

	private User(Long id) {
		this.id = id;
	}

	public static User createNewSocialUser(OAuthAttributes oauthAttributes) {
		String email = oauthAttributes.getEmail();
		String username = oauthAttributes.getUsername();
		//TODO 최초가입자 닉네임 랜덤 생성 && Oauth타입알아내기
		// edited at 2024-04-25
		// author
		OauthType oauthType = oauthAttributes.getOauthType();
		String nickname = "최초로그인시닉네임";
		return new User(email, username, nickname, oauthType, UserRole.USER);
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
			throw new ExpectedException(ErrorCode.CannotRestoreUser);
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
		this.roles = userRole.getStringValue();
	}

	@Builder
	public User(List<PictureProfile> pictureProfileList, List<PictureUserFace> pictureUserFaceList, String email,
		String introduction,
		String username, String nickname, UserStatus userStatus, Boolean emailVerified, String loginId, String password,
		Creator creator, UserRole userRole, String roles, OauthType lastLoginSocialPlatform, LocalDateTime deletedAt) {
		this.pictureProfileList = pictureProfileList;
		this.pictureUserFaceList = pictureUserFaceList;
		this.email = email;
		this.introduction = introduction;
		this.username = username;
		this.nickname = nickname;
		this.userStatus = userStatus;
		this.emailVerified = emailVerified;
		this.loginId = loginId;
		this.password = password;
		this.creator = creator;
		this.userRole = userRole;
		this.roles = roles;
		this.lastLoginSocialPlatform = lastLoginSocialPlatform;
		this.deletedAt = deletedAt;
	}
}
