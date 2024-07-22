package com.gt.genti.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserRepository userRepository;
	private final CreatorRepository creatorRepository;

	public User getAdminUser(){
		return userRepository.findAdminUser(PageRequest.of(0,1)).get(0);
	}
	public Creator getAdminCreator(){
		try{
			return creatorRepository.findAdminCreator(PageRequest.of(0,1)).get(0);
		} catch (Exception e){
			log.error("어드민 유저가 존재하지 않습니다.");
			return null;
		}
	}


}
