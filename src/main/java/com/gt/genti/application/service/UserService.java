package com.gt.genti.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Picture;
import com.gt.genti.domain.User;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public UserInfoResponseDto getUserInfo(Long id) {
		User findUser = userRepository.findById(id).orElseThrow();
		if (!findUser.isActivate()) {
			throw new RuntimeException("탈퇴");
		}
		Picture picture = findUser.getProfilePicture().getPicture();
		return UserInfoResponseDto.builder()
			.user(findUser)
			.picture(picture)
			.build();
	}

	@Transactional
	public UserInfoResponseDto updateUserInfo(Long id, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User findUser = userRepository.findById(id).orElseThrow();
		if (!findUser.isActivate()) {
			throw new RuntimeException("탈퇴");
		}
		findUser.update(userInfoUpdateRequestDto);
		return UserInfoResponseDto.builder()
			.user(findUser)
			.picture(findUser.getProfilePicture().getPicture())
			.build();
	}

	@Transactional
	public Boolean deleteUserInfoSoft(Long id) {
		User findUser = userRepository.findById(id).orElseThrow();
		if (!findUser.isActivate()) {
			throw new RuntimeException("이미 탈퇴");
		}
		findUser.softDelete();
		return true;
	}

	@Transactional
	public Boolean restoreSoftDeletedUser(Long id) {
		User findUser = userRepository.findById(id).orElseThrow();
		if (findUser.isActivate()) {
			throw new RuntimeException("삭제되지 않은 사용자에 대한 잘못된 요청");
		}
		findUser.restore();
		return true;
	}
}
