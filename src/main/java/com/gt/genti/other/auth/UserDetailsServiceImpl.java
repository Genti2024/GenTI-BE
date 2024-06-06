package com.gt.genti.other.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
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
		return userRepository.findByEmail(email)
			.map(
				findUser -> new UserDetailsImpl(findUser,
					findUser.getRoles()))
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound, email));
	}

	public UserDetailsImpl loadUserById(Long id) throws UsernameNotFoundException {
		return userRepository.findById(id)
			.map(
				findUser -> new UserDetailsImpl(findUser,
					findUser.getRoles()))
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound, "찾은 id : " + id));
	}
}