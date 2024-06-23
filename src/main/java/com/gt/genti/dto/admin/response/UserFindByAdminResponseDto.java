package com.gt.genti.dto.admin.response;

import java.time.LocalDateTime;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.Sex;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.dto.creator.response.CreatorFindResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class UserFindByAdminResponseDto {
	@Schema(name = "id")
	Long id;

	@Schema(name = "email")
	String email;

	@Schema(name = "introduction")
	String introduction;

	@Schema(name = "username")
	String username;

	@Schema(name = "nickname")
	String nickname;
	@Schema(name = "sex")
	Sex sex;

	@Schema(name = "userStatus")
	UserStatus userStatus;

	@Schema(name = "emailVerified")
	Boolean emailVerified;

	@Schema(name = "loginId")
	String loginId;

	@Schema(name = "password")
	String password;

	@Schema(name = "creator")
	CreatorFindResponseDto creator;

	@Schema(name = "userRole")
	UserRole userRole;

	@Schema(name = "lastLoginSocialPlatform")
	OauthType lastLoginSocialPlatform;

	@Schema(name = "deletedAt")
	LocalDateTime deletedAt;

	@Schema(name = "deposit")
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
		this.lastLoginSocialPlatform = user.getLastLoginSocialPlatform();
		this.deletedAt = user.getDeletedAt();
		this.deposit = new DepositFindResponseDto(user.getDeposit());
		this.sex = user.getSex();
	}

}
