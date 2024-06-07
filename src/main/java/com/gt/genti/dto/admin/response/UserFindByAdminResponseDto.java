package com.gt.genti.dto.admin.response;

import java.time.LocalDateTime;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.Sex;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.dto.creator.response.CreatorFindResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindByAdminResponseDto {
	Long id;

	String email;

	String introduction;

	String username;

	String nickname;
	Sex sex;

	UserStatus userStatus;

	Boolean emailVerified;

	String loginId;

	String password;

	CreatorFindResponseDto creator;

	UserRole userRole;

	String roles;

	OauthType lastLoginSocialPlatform;

	LocalDateTime deletedAt;

	DepositFindResponseDto deposit;

	public UserFindByAdminResponseDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.introduction = user.getIntroduction();
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.userStatus = user.getUserStatus();
		this.emailVerified = user.getEmailVerified();
		this.loginId = user.getLoginId();
		this.password = user.getPassword();
		if (user.getCreator() != null) {
			this.creator = new CreatorFindResponseDto(user.getCreator());
		}
		this.userRole = user.getUserRole();
		this.roles = user.getRoles();
		this.lastLoginSocialPlatform = user.getLastLoginSocialPlatform();
		this.deletedAt = user.getDeletedAt();
		this.deposit = new DepositFindResponseDto(user.getDeposit());
		this.sex = user.getSex();
	}

}
