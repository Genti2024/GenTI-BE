package com.gt.genti.config.auth;

import java.io.Serializable;
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

public class UserDetailsImpl implements UserDetails, OAuth2User, Serializable {

	private static final long serialVersionUID = 174726374856727L;

	private String id;    // DB에서 PK 값
	private String loginId;        // 로그인용 ID 값
	private String password;    // 비밀번호
	private String email;    //이메일
	private boolean emailVerified;    //이메일 인증 여부
	private boolean locked;    //계정 잠김 여부
	private String nickname;    //닉네임
	private Collection<GrantedAuthority> authorities;    //권한 목록

	@Getter
	private User user;
	private Map<String, Object> attributes;

	//non socical 용
	@Builder
	public UserDetailsImpl(User user, String roles) {
		//PrincipalOauth2UserService 참고
		this.user = user;
		this.id = user.getId().toString();
		this.authorities = createAuthorities(roles);
	}

	// social 용
	@Builder
	public UserDetailsImpl(User user, String roles, Map<String, Object> attributes) {
		//PrincipalOauth2UserService 참고
		this.user = user;
		this.id = user.getId().toString();
		this.authorities = createAuthorities(roles);
		this.attributes = attributes;
	}

	public Long getId() {
		return Long.parseLong(this.id);
	}

	//Non Social + Employer 로그인 용도
	@Builder
	public UserDetailsImpl(Long authId, String roles, String userEmail, String userPw, boolean emailVerified,
		boolean locked) {
		this.id = String.valueOf(authId);
		this.authorities = createAuthorities(roles);
		this.email = userEmail;
		this.password = userPw;
		this.emailVerified = emailVerified;
		this.locked = !locked;
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

	/**
	 * 해당 유저의 권한 목록
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * 비밀번호
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * PK값
	 */
	@Override
	public String getUsername() {
		return id;
	}

	/**
	 * 계정 만료 여부
	 * true : 만료 안됨
	 * false : 만료
	 *
	 * @return
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 계정 잠김 여부
	 * true : 잠기지 않음
	 * false : 잠김
	 *
	 * @return
	 */
	@Override
	public boolean isAccountNonLocked() {
		return locked;
	}

	/**
	 * 비밀번호 만료 여부
	 * true : 만료 안됨
	 * false : 만료
	 *
	 * @return
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 사용자 활성화 여부
	 * ture : 활성화
	 * false : 비활성화
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		//이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
		//상식과 조금 벗어나서, Customizing 하였음
		return (emailVerified && locked);

	}

	@Override
	public String getName() {
		return attributes.get("sub").toString();
	}
}
