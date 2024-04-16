package com.gt.genti.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
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

	@Column(name = "oauth_picture_url")
	String oauthPictureUrl;

	@Column(name = "username", nullable = false)
	String username;

	@Column(name = "user_role", nullable = false)
	@Convert(converter = UserRoleConverter.class)
	UserRole userRole;

	@Column(name = "user_status", nullable = false)
	@Convert(converter = UserStatusConverter.class)
	UserStatus userStatus;

	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.DETACH}, orphanRemoval = true)
	@JoinColumn(name = "creator_id")
	Creator creator;

	@Column(name = "deleted_at")
	LocalDateTime deletedAt;

	@Builder
	public User(String email, String username, String oauthPictureUrl, UserRole userRole, UserStatus userStatus) {
		this.email = email;
		this.username = username;
		this.oauthPictureUrl = oauthPictureUrl;
		this.userRole = userRole;
		this.userStatus = userStatus;
	}

	public User updateByOauth(String name, String oauthPictureUrl) {
		this.username = name;
		this.oauthPictureUrl = oauthPictureUrl;
		return this;
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
		if(Period.between(this.deletedAt.toLocalDate(), LocalDate.now()).getMonths() >=1){
			throw new RuntimeException("탈퇴한 지 한달이 지난 경우 재가입해야합니다.");
		}
		this.userStatus = UserStatus.ACTIVATED;

	}

	public Boolean isActivate() {
		return this.userStatus == UserStatus.ACTIVATED;
	}
}
