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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //TODO redis json 직렬화시 type id Type id handling not implemented for (by serializer  오류 발생
        // edited at 2024-07-08
        // author 서병렬
        // return userRepository.findByIdWithCache(Long.parseLong(userId))
        return userRepository.findById(Long.parseLong(userId))
                .map(UserPrincipal::new)
                .orElseThrow(()-> ExpectedException.withLogging(ResponseCode.UnAuthorized));
    }

}
