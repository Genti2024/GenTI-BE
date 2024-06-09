package com.gt.genti.other.auth;

import static com.gt.genti.other.auth.UserDetailsImpl.*;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.User;
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
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(
				UserDetailsServiceImpl::convertToUserDetails)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound, email));
	}

	@Transactional
	public UserDetailsImpl loadUserById(Long id) throws UsernameNotFoundException {
		return userRepository.findById(id)
			.map(
				UserDetailsServiceImpl::convertToUserDetails)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound, "찾은 id : " + id));
	}

	@NotNull
	private static UserDetailsImpl convertToUserDetails(User foundUser) {
		if (!foundUser.isLogin()) {
			throw new ExpectedException(DomainErrorCode.UserNotLoggedIn);
		}
		foundUser.login();
		return CreateRegisteredUserDetails(foundUser,
			foundUser.getUserRole().getRoleString());
	}
}