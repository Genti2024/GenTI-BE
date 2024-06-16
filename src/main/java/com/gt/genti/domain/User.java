package com.gt.genti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.Sex;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.domain.enums.converter.db.OauthTypeConverterIgnoreCase;
import com.gt.genti.domain.enums.converter.db.SexConverter;
import com.gt.genti.domain.enums.converter.db.UserRoleConverter;
import com.gt.genti.domain.enums.converter.db.UserStatusConverter;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.OAuthAttributes;
import com.gt.genti.other.util.RandomUtils;

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

	@Column(name = "last_login_social_platform")
	@Convert(converter = OauthTypeConverterIgnoreCase.class)
	OauthType lastLoginSocialPlatform;

	@Column(name = "deleted_at")
	LocalDateTime deletedAt;

	@Column(name = "last_login_date", nullable = false)
	LocalDateTime lastLoginDate;

	@Column(name = "login", nullable = false)
	Boolean login;

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
		if (this.login == null) {
			this.login = true;
		}
		if (this.sex == null){
			this.sex = Sex.NONE;
		}
		if(this.introduction == null){
			this.introduction = "";
		}

		if(this.requestTaskCount == null){
			this.requestTaskCount = 0;
		}
	}

	public static User createNewSocialUser(OAuthAttributes oauthAttributes) {
		String email = oauthAttributes.getEmail();
		String username = oauthAttributes.getUsername();
		OauthType oauthType = oauthAttributes.getOauthType();
		String nickname = RandomUtils.generateRandomNickname();
		return new User(email, username, nickname, oauthType, UserRole.OAUTH_FIRST_JOIN);
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
			throw ExpectedException.withLogging(DomainErrorCode.CannotRestoreUser);
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

	private User(String email, String username, String nickname, OauthType oauthType, UserRole userRole) {
		this.email = email;
		this.username = username;
		this.nickname = nickname;
		this.userRole = userRole;
		this.lastLoginSocialPlatform = oauthType;
		this.userStatus = UserStatus.ACTIVATED;
		this.login();
		this.requestTaskCount = 0;
	}

	public void login() {
		this.login = true;
		this.lastLoginDate = LocalDateTime.now();
	}

	public void logout() {
		this.login = false;
	}

	public Boolean isLogin() {
		return this.login;
	}

	@Builder
	public User(List<PictureProfile> pictureProfileList, List<PictureUserFace> pictureUserFaceList, String email,
		String introduction,
		String username, String nickname, UserStatus userStatus, Boolean emailVerified, String loginId, String password,
		Creator creator, UserRole userRole, OauthType lastLoginSocialPlatform, LocalDateTime deletedAt, LocalDateTime lastLoginDate) {
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
		this.lastLoginSocialPlatform = lastLoginSocialPlatform;
		this.deletedAt = deletedAt;
		this.lastLoginDate = lastLoginDate;
	}

	public void addRequestCount() {
		this.requestTaskCount += 1;
	}
}
