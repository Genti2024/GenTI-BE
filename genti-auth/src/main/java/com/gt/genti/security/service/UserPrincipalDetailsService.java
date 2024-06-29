package com.gt.genti.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gt.genti.user.model.UserPrincipal;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByIdWithCache(Long.parseLong(username))
                .map(UserPrincipal::new)
                .orElseThrow(()-> ExpectedException.withLogging(ResponseCode.UnAuthorized));
    }

}
