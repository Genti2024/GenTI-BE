package com.gt.genti.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.dto.ChangeUserRoleDto;
import com.gt.genti.dto.ChangeUserStatusDto;
import com.gt.genti.dto.CommonPictureResponseDto;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoResponseDtoForAdmin;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.PictureCompletedRepository;
import com.gt.genti.repository.UserRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final PictureService pictureService;
	private final UserRepository userRepository;
	private final DepositService depositService;
	private final CreatorRepository creatorRepository;
	private final PictureCompletedRepository pictureCompletedRepository;

	@Transactional
	public UserInfoResponseDto getUserInfo(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		return new UserInfoResponseDto(foundUser);
	}

	@Transactional
	public UserInfoResponseDto updateUserInfo(Long userId, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.updateName(userInfoUpdateRequestDto.getUserName());
		if (!(userInfoUpdateRequestDto.getProfilePictureUrl().isEmpty()
			|| userInfoUpdateRequestDto.getProfilePictureUrl().isBlank())) {
			PictureProfile foundPictureProfile = pictureService.findByUrlPictureProfile(
				userInfoUpdateRequestDto.getProfilePictureUrl());
			foundUser.addProfilePicture(foundPictureProfile);
		}

		return new UserInfoResponseDto(foundUser);
	}

	@Transactional
	public Boolean updateUserRole(Long userId, ChangeUserRoleDto changeUserRoleDto) {
		User foundUser = findActivateUserByUserId(userId);
		UserRole userRole = changeUserRoleDto.getUserRole();
		foundUser.updateUserRole(userRole);
		if (userRole == UserRole.CREATOR) {
			Creator newCreator = new Creator(foundUser);
			creatorRepository.save(newCreator);
			depositService.createDeposit(foundUser);
		} else if (userRole == UserRole.ADMIN) {
			Creator newCreator = new Creator(foundUser);
			creatorRepository.save(newCreator);
		}
		return true;
	}

	@Transactional
	public Boolean deleteUserInfoSoft(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.softDelete();
		return true;
	}

	@Transactional
	public Boolean restoreSoftDeletedUser(Long userId) {
		User foundUser = findDeactivatedUserByUserId(userId);
		foundUser.restore();
		return true;
	}

	@Transactional
	public Boolean updateUserStatus(Long userId, ChangeUserStatusDto changeUserStatusDto) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.updateStatus(changeUserStatusDto.getUserStatus());
		return true;
	}

	public List<CommonPictureResponseDto> getAllMyGeneratedPicture(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		log.info("userId" + userId);
		List<PictureCompleted> pictureCompletedList = pictureCompletedRepository.findAllByUser(foundUser);
		return pictureCompletedList.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}

	private User findActivateUserByUserId(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));

		if (!foundUser.isActivate()) {
			throw new ExpectedException(ErrorCode.UserDeactivated);
		}
		return foundUser;
	}

	private User findDeactivatedUserByUserId(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));

		if (foundUser.isActivate()) {
			throw new ExpectedException(ErrorCode.AlreadyActivatedUser);
		}
		return foundUser;
	}

	public List<UserInfoResponseDtoForAdmin> getAllUserInfo() {
		return userRepository.findAll().stream().map(UserInfoResponseDtoForAdmin::new).toList();
	}

	public List<UserInfoResponseDtoForAdmin> getAllUserInfo(UserRole userRole) {
		return null;
	}
}
