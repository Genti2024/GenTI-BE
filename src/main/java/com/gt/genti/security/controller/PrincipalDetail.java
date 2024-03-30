package com.gt.genti.security.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gt.genti.domain.User;

import lombok.Data;

@Data
public class PrincipalDetail implements UserDetails, OAuth2User {

	private User user;
	private Collection<? extends GrantedAuthority> authorities;

	private Map<String, Object> attributes;

	// oauth2.0에 사용됨
	public PrincipalDetail(User user, Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	public PrincipalDetail(User user, Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes) {
		this.user = user;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	// info 에 들어가는 것들이 토큰에 들어가는 데이터
	public Map<String, Object> getMemberInfo() {
		Map<String, Object> info = new HashMap<>();
		info.put("username", user.getUsername());
		info.put("email", user.getEmail());
		info.put("userRole", user.getRoleKey());
		return info;
	}

	@Override
	public String getName() {
		return user.getEmail();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}