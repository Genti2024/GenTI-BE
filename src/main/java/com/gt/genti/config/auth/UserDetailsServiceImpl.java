package com.gt.genti.config.auth;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gt.genti.repository.UserRepository;
import com.gt.genti.security.controller.PrincipalDetail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("--------------------------- UserDetailsServiceImpl ---------------------------");
		log.info("username = {}", username);

		return userRepository.findByEmail(username)
			.map(
				findUser -> {
					if (!findUser.isActivate()) {
						throw new RuntimeException("탈퇴한 사용자입니다.");
					}
					return new PrincipalDetail(findUser,
						Collections.singleton(new SimpleGrantedAuthority(findUser.getRoleKey())));
				})
			.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다"));
	}
}