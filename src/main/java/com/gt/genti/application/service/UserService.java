package com.gt.genti.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.dto.ChangeUserRoleDto;
import com.gt.genti.dto.ChangeUserStatusDto;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.DepositRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final DepositService depositService;
	private final CreatorRepository creatorRepository;

	@Transactional
	public UserInfoResponseDto getUserInfo(Long id) {
		User findUser = userRepository.findById(id).orElseThrow();
		if (!findUser.isActivate()) {
			throw new RuntimeException("탈퇴");
		}
		PictureProfile pictureProfile = findUser.getPictureProfile();
		return UserInfoResponseDto.builder()
			.user(findUser)
			.pictureProfile(pictureProfile)
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
			.pictureProfile(findUser.getPictureProfile())
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

	@Transactional
	public Boolean updateUserStatus(Long userId, ChangeUserStatusDto changeUserStatusDto) {
		User foundUser = findUserById(userId);
		foundUser.updateStatus(changeUserStatusDto.getUserStatus());
		return true;
	}



	@Transactional
	public Boolean updateUserRole(Long userId, ChangeUserRoleDto changeUserRoleDto) {
		User foundUser = findUserById(userId);
		foundUser.updateUserRole(changeUserRoleDto.getUserRole());
		Creator newCreator = new Creator(foundUser);
		creatorRepository.save(newCreator);
		depositService.createDeposit(foundUser);
		return true;
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));
	}
}
