package com.gt.genti.other.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import com.gt.genti.domain.User;

import lombok.Builder;
import lombok.Getter;

public class UserDetailsImpl implements Principal, UserDetails, OAuth2User, Serializable {

	private static final long serialVersionUID = 174726374856727L;

	private String id;    // DB에서 PK 값
	private String loginId;        // 로그인용 ID 값
	private String password;    // 비밀번호
	@Getter
	private String email;    //이메일
	private boolean emailVerified;    //이메일 인증 여부
	private boolean locked;    //계정 잠김 여부
	private String nickname;    //닉네임
	private Collection<GrantedAuthority> authorities;    //권한 목록

	@Getter
	private User user;
	private Map<String, Object> attributes;

	@Builder
	public UserDetailsImpl(User user, String roles) {
		this.user = user;
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.id = user.getId().toString();
		this.authorities = createAuthorities(roles);
	}

	//social 최초가입
	@Builder
	public UserDetailsImpl(User user, Map<String, Object> attributes) {
		this.user = user;
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.id = user.getId().toString();
		this.authorities = createAuthorities(user.getRoles());
		this.attributes = attributes;
	}

	public Long getId() {
		return Long.parseLong(this.id);
	}

	@Builder
	public UserDetailsImpl(Long authId, String roles, String userEmail, String userPw, boolean emailVerified,
		boolean locked) {
		this.id = String.valueOf(authId);
		this.authorities = createAuthorities(roles);
		this.email = userEmail;
		this.password = userPw;
		this.emailVerified = emailVerified;
		this.locked = locked;
	}

	private Collection<GrantedAuthority> createAuthorities(String roles) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		for (String role : roles.split(",")) {
			if (!StringUtils.hasText(role))
				continue;
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isActivate();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.isActivate();
	}

	@Override
	public String getName() {
		return this.user.getNickname();
	}
}
