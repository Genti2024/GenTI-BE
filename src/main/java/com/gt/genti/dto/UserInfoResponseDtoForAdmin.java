package com.gt.genti.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.Sex;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;

public class UserInfoResponseDtoForAdmin {
	Long id;

	List<PictureProfile> pictureProfileList;

	List<PictureUserFace> pictureUserFaceList;

	String email;

	String introduction;

	String username;

	String nickname;
	Sex sex;

	UserStatus userStatus;

	Boolean emailVerified;

	String loginId;

	String password;

	CreatorInfoResponseDto creator;

	UserRole userRole;

	String roles;

	OauthType lastLoginSocialPlatform;

	LocalDateTime deletedAt;

	Deposit deposit;

	public UserInfoResponseDtoForAdmin(User user) {
		this.id = user.getId();
		this.pictureProfileList = user.getPictureProfileList();
		this.pictureUserFaceList = user.getPictureUserFaceList();
		this.email = user.getEmail();
		this.introduction = user.getIntroduction();
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.userStatus = user.getUserStatus();
		this.emailVerified = user.getEmailVerified();
		this.loginId = user.getLoginId();
		this.password = user.getPassword();
		this.creator = new CreatorInfoResponseDto(user.getCreator());
		this.userRole = user.getUserRole();
		this.roles = user.getRoles();
		this.lastLoginSocialPlatform = user.getLastLoginSocialPlatform();
		this.deletedAt = user.getDeletedAt();
		this.deposit = user.getDeposit();
		this.sex = user.getSex();
	}

}
