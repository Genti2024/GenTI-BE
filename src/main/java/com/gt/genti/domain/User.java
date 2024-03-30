package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@OneToOne
	Picture profilePictureId;

	@NotNull
	String email;

	String introduction;

	@NotNull
	String username;

	String oauthPictureUrl;

	@NotNull
	UserRole userRole;

	@Builder
	public User(String email, String username, String oauthPictureUrl, UserRole userRole) {
		this.email = email;
		this.username = username;
		this.oauthPictureUrl = oauthPictureUrl;
		this.userRole = userRole;
	}

	public User update(String name, String oauthPictureUrl) {
		this.username = name;
		this.oauthPictureUrl = oauthPictureUrl;
		return this;
	}

	public String getRoleKey() {
		return this.getUserRole().getRole();
	}
}
