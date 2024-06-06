package com.gt.genti.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.dto.CommonPictureUrlResponseDto;
import com.gt.genti.dto.UserFindByAdminResponseDto;
import com.gt.genti.dto.UserFindResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.dto.UserRoleUpdateRequestDto;
import com.gt.genti.dto.UserStatusUpdateRequestDto;
import com.gt.genti.error.DomainErrorCode;
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
	public UserFindResponseDto getUserInfo(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		return new UserFindResponseDto(foundUser);
	}

	@Transactional
	public UserFindResponseDto updateUserInfo(Long userId, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.updateName(userInfoUpdateRequestDto.getUserName());
		if (!(userInfoUpdateRequestDto.getProfilePictureUrl().isEmpty()
			|| userInfoUpdateRequestDto.getProfilePictureUrl().isBlank())) {
			PictureProfile foundPictureProfile = pictureService.findByUrlPictureProfile(
				userInfoUpdateRequestDto.getProfilePictureUrl());
			foundUser.addProfilePicture(foundPictureProfile);
		}

		return new UserFindResponseDto(foundUser);
	}

	@Transactional
	public Boolean updateUserRole(Long userId, UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
		User foundUser = findActivateUserByUserId(userId);
		UserRole userRole = userRoleUpdateRequestDto.getUserRole();
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
	public Boolean updateUserStatus(Long userId, UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.updateStatus(userStatusUpdateRequestDto.getUserStatus());
		return true;
	}

	public List<CommonPictureUrlResponseDto> getAllMyGeneratedPicture(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		log.info("userId" + userId);
		List<PictureCompleted> pictureCompletedList = pictureCompletedRepository.findAllByUser(foundUser);
		return pictureCompletedList.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}

	public Page<UserFindByAdminResponseDto> getAllUserInfo(int page, int size) {
		if (page < 0) {
			page = 0;
		}

		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findAll(pageable).map(UserFindByAdminResponseDto::new);
	}

	public Page<UserFindByAdminResponseDto> getAllUserInfo(UserRole userRole, int page, int size) {
		return null;
	}

	private User findDeactivatedUserByUserId(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound));

		if (foundUser.isActivate()) {
			throw new ExpectedException(DomainErrorCode.AlreadyActivatedUser);
		}
		return foundUser;
	}

	private User findActivateUserByUserId(Long userId) {
		User foundUser = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(DomainErrorCode.UserNotFound));

		if (!foundUser.isActivate()) {
			throw new ExpectedException(DomainErrorCode.UserDeactivated);
		}
		return foundUser;
	}
}
