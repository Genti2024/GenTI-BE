package com.gt.genti.other.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("--------------------------- UserDetailsServiceImpl ---------------------------");
		log.info("email = {}", email);

		return userRepository.findByEmail(email)
			.map(
				findUser -> new UserDetailsImpl(findUser,
					UserRole.getAllRoles(findUser.getUserRole())))
			.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다"));
	}
}