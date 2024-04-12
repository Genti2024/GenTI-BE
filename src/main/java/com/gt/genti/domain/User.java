package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.domain.enums.converter.UserRoleConverter;
import com.gt.genti.domain.enums.converter.UserStatusConverter;
import com.gt.genti.dto.UserInfoUpdateRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	String email;

	String introduction;

	@NotNull
	String username;

	String oauthPictureUrl;

	@NotNull
	@Convert(converter = UserRoleConverter.class)
	UserRole userRole;

	@NotNull
	@Convert(converter = UserStatusConverter.class)
	UserStatus userStatus;

	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.DETACH}, orphanRemoval = true)
	@JoinColumn(name = "creator_id")
	Creator creator;

	@Builder
	public User(String email, String username, String oauthPictureUrl, UserRole userRole) {
		this.email = email;
		this.username = username;
		this.oauthPictureUrl = oauthPictureUrl;
		this.userRole = userRole;
	}

	public User updateByOauth(String name, String oauthPictureUrl) {
		this.username = name;
		this.oauthPictureUrl = oauthPictureUrl;
		return this;
	}

	public String getRoleKey() {
		return this.getUserRole().getRole();
	}

	public void update(UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		this.username = userInfoUpdateRequestDto.getUserName();
		this.getProfilePicture().getPicture().setUrl(userInfoUpdateRequestDto.getProfilePictureUrl());
	}

	public void softDelete() {
		this.userStatus = UserStatus.DEACTIVATED;
	}

	public Boolean isActivate() {
		return this.userStatus == UserStatus.ACTIVATED;
	}
}
