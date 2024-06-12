package com.gt.genti.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.dto.admin.request.UserRoleUpdateRequestDto;
import com.gt.genti.dto.admin.request.UserStatusUpdateRequestDto;
import com.gt.genti.dto.admin.response.UserFindByAdminResponseDto;
import com.gt.genti.dto.common.response.CommonPictureUrlResponseDto;
import com.gt.genti.dto.user.request.UserInfoUpdateRequestDto;
import com.gt.genti.dto.user.response.UserFindResponseDto;
import com.gt.genti.dto.user.response.UserInfoUpdateResponseDto;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.OAuthAttributeBuilder;
import com.gt.genti.other.auth.OAuthAttributes;
import com.gt.genti.other.auth.UserDetailsImpl;
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
	public UserFindResponseDto getUserInfo(User user) {
		User foundUser = findUser(user.getId());
		List<CommonPictureUrlResponseDto> profilePictureResponseList = null;
		if (!foundUser.getPictureProfileList().isEmpty()) {
			profilePictureResponseList = foundUser.getPictureProfileList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList();
		}

		return new UserFindResponseDto(foundUser.getId(), foundUser.getUsername(), foundUser.getNickname(),
			profilePictureResponseList);
	}

	@Transactional
	public UserInfoUpdateResponseDto updateUserInfo(User user, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User foundUser = findUser(user.getId());
		foundUser.updateName(userInfoUpdateRequestDto.getUserName());
		if (!(userInfoUpdateRequestDto.getProfilePictureUrl().isEmpty()
			|| userInfoUpdateRequestDto.getProfilePictureUrl().isBlank())) {
			PictureProfile foundPictureProfile = pictureService.findByUrlPictureProfile(
				userInfoUpdateRequestDto.getProfilePictureUrl());
			foundUser.addProfilePicture(foundPictureProfile);
		}
		List<CommonPictureUrlResponseDto> profilePictureResponseList = null;
		if (!foundUser.getPictureProfileList().isEmpty()) {
			profilePictureResponseList = foundUser.getPictureProfileList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList();
		}

		return UserInfoUpdateResponseDto.builder()
			.profilePictureList(profilePictureResponseList)
			.id(foundUser.getId())
			.nickname(foundUser.getNickname())
			.username(foundUser.getUsername())
			.build();
	}

	@Transactional
	public Boolean updateUserRole(Long userId, UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
		User foundUser = findUser(userId);
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
	public Boolean deleteUserInfoSoft(User user) {
		user.softDelete();
		return true;
	}

	@Transactional
	public Boolean restoreSoftDeletedUser(User user) {
		user.restore();
		return true;
	}

	@Transactional
	public Boolean updateUserStatus(Long userId, UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
		User foundUser = findUser(userId);
		foundUser.updateStatus(userStatusUpdateRequestDto.getUserStatus());
		return true;
	}

	public List<CommonPictureUrlResponseDto> getAllMyGeneratedPicture(User user) {
		List<PictureCompleted> pictureCompletedList = pictureCompletedRepository.findAllByUser(user);
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

	@Transactional
	public Boolean logout(UserDetailsImpl userDetails) {
		userDetails.getUser().logout();
		return true;
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.UserNotFound));
	}

	public Optional<User> findOptionalUser(String email){
		return userRepository.findByEmail(email);
	}

	public User createNewUser(User user) {
		depositService.createDeposit(user);
		return userRepository.save(user);
	}
}
