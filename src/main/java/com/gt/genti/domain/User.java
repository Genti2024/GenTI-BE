package com.gt.genti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.gt.genti.config.auth.OAuthAttributes;
import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.domain.enums.converter.OauthTypeConverter;
import com.gt.genti.domain.enums.converter.UserRoleConverter;
import com.gt.genti.domain.enums.converter.UserStatusConverter;
import com.gt.genti.dto.UserInfoUpdateRequestDto;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	// profile_picture는 완전히 user에 종속되어있다
	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.DETACH}, orphanRemoval = true)
	@JoinColumn(name = "profile_picture_id")
	ProfilePicture profilePicture;

	@OneToMany
	@JoinColumn(name = "user_id")
	List<UserFacePicture> userFacePictureList;

	@OneToMany
	@JoinColumn(name = "user_id")
	List<Picture> createdPictureList;

	@Column(name = "email")
	String email;

	@Column(name = "introduction")
	String introduction;

	@Column(name = "username", nullable = false)
	String username;

	@Column(name = "nickname")
	String nickname;

	@Column(name = "user_role", nullable = false)
	@Convert(converter = UserRoleConverter.class)
	UserRole userRole;

	@Column(name = "user_status", nullable = false)
	@Convert(converter = UserStatusConverter.class)
	UserStatus userStatus;

	@Column(name = "email_verified")
	Boolean emailVerified;

	@Column(name = "login_id")
	String loginId;
	@Column(name = "password")
	String password;

	@OneToOne
	@JoinColumn(name = "creator_id")
	Creator creator;

	@Column(name = "last_login_social_platform")
	@Convert(converter = OauthTypeConverter.class)
	OauthType lastLoginSocialPlatform;

	@Column(name = "deleted_at")
	LocalDateTime deletedAt;

	String roles;

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
		this.lastLoginSocialPlatform = oauthType;
		this.userStatus = UserStatus.ACTIVATED;
	}

	private User(Long id) {
		this.id = id;
	}

	public static User createNewSocialUser(OAuthAttributes attributes) {
		String email = attributes.getEmail();
		String username = attributes.getName();
		//TODO 최초가입자 닉네임 랜덤 생성 && Oauth타입알아내기
		// edited at 2024-04-25
		// author
		OauthType oauthType = OauthType.GOOGLE;
		String nickname = "최초로그인시닉네임";
		return new User(email, username, nickname, oauthType, UserRole.USER);
	}

	public String getRole() {
		return this.getUserRole().getStringValue();
	}

	public void update(UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		this.username = userInfoUpdateRequestDto.getUserName();
		this.getProfilePicture().getPicture().modify(userInfoUpdateRequestDto.getProfilePictureUrl());
	}

	public void softDelete() {
		this.userStatus = UserStatus.DEACTIVATED;
		this.deletedAt = LocalDateTime.now();
	}

	public void restore() {
		if (Period.between(this.deletedAt.toLocalDate(), LocalDate.now()).getMonths() >= 1) {
			throw new RuntimeException("탈퇴한 지 한달이 지난 경우 재가입해야합니다.");
		}
		this.userStatus = UserStatus.ACTIVATED;

	}

	public Boolean isActivate() {
		return this.userStatus == UserStatus.ACTIVATED;
	}
}
