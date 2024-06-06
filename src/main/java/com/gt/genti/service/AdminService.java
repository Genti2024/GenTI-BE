package com.gt.genti.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.User;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserRepository userRepository;
	private final CreatorRepository creatorRepository;

	public User getAdminUser(){
		return userRepository.findAdminUser(PageRequest.of(0,1)).get(0);
	}
	public Creator getAdminCreator(){
		return creatorRepository.findAdminCreator(PageRequest.of(0,1)).get(0);
	}


}
