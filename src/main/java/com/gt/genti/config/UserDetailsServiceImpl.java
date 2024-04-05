package com.gt.genti.config;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gt.genti.domain.repository.UserRepository;
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
				user -> new PrincipalDetail(user, Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey()))))
			.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다"));
	}
}