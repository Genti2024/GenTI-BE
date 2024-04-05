package com.gt.genti.config.auth;

import java.io.Serializable;
import com.gt.genti.domain.User;
import lombok.Getter;

@Getter
public class SessionUserDto implements Serializable {
	private String name;
	private String email;
	private String picture;

	// oauth에서 가져온 정보만 유지
	public SessionUserDto(User user) {
		this.name = user.getUsername();
		this.email = user.getEmail();
		this.picture = user.getOauthPictureUrl();
	}
}
