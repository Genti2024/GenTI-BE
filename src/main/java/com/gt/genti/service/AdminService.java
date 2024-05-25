package com.gt.genti.service;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserRepository userRepository;
	private final CreatorRepository creatorRepository;

	public User getAdminUser(){
		return userRepository.findAdminUser().orElseThrow(()-> new ExpectedException(ErrorCode.UnHandledException));
	}
	public Creator getAdminCreator(){
		return creatorRepository.findAdminCreator().orElseThrow(()-> new ExpectedException(ErrorCode.UnHandledException));
	}


}
