package com.gt.genti.application.service;

import static com.gt.genti.error.ResponseCode.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.dto.admin.request.UserRoleUpdateRequestDto;
import com.gt.genti.dto.admin.request.UserStatusUpdateRequestDto;
import com.gt.genti.dto.admin.response.UserFindByAdminResponseDto;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.dto.user.request.UserInfoUpdateRequestDto;
import com.gt.genti.dto.user.response.UserFindResponseDto;
import com.gt.genti.error.ExpectedException;
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
		List<CommonPictureResponseDto> profilePictureResponseList = null;
		if (!foundUser.getPictureProfileList().isEmpty()) {
			profilePictureResponseList = foundUser.getPictureProfileList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList();
		}

		return UserFindResponseDto.of(foundUser);
	}

	@Transactional
	public UserFindResponseDto updateUserInfo(User user, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User foundUser = findUser(user.getId());
		foundUser.updateName(userInfoUpdateRequestDto.getUserName());
		if (userInfoUpdateRequestDto.getProfilePicture() != null) {
			PictureProfile foundPictureProfile = pictureService.findByKeyPictureProfile(
				userInfoUpdateRequestDto.getProfilePicture().getKey());
			foundUser.addProfilePicture(foundPictureProfile);
		}
		List<CommonPictureResponseDto> profilePictureResponseList = null;
		if (!foundUser.getPictureProfileList().isEmpty()) {
			profilePictureResponseList = foundUser.getPictureProfileList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList();
		}

		return UserFindResponseDto.builder()
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
	public Boolean deleteUserSoft(User user) {
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

	public Page<CommonPictureResponseDto> getAllMyGeneratedPicture(User user, Pageable pageable) {
		return pictureCompletedRepository.findAllByUserPagination(user, pageable)
			.map(PictureEntity::mapToCommonResponse);
	}

	public Page<UserFindByAdminResponseDto> getAllUserInfo(Pageable pageable) {
		return userRepository.findAll(pageable).map(mapToUserFindByAdminResponseDto());
	}

	public Page<UserFindByAdminResponseDto> getAllUserInfoByUserRole(UserRole userRole, Pageable pageable) {
		return userRepository.findAllByUserRole(pageable, userRole)
			.map(mapToUserFindByAdminResponseDto());
	}

	@Transactional
	public Boolean logout(UserDetailsImpl userDetails) {
		userDetails.getUser().logout();
		return true;
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(UserNotFound, userId.toString()));
	}

	public Optional<User> findOptionalUser(String email) {
		return userRepository.findByEmail(email);
	}

	public User createNewUser(User user) {
		depositService.createDeposit(user);
		return userRepository.save(user);
	}

	@NotNull
	private Function<User, UserFindByAdminResponseDto> mapToUserFindByAdminResponseDto() {
		return user -> UserFindByAdminResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.userRole(user.getUserRole())
			.birthDate(user.getBirthDate())
			.sex(user.getSex())
			.userStatus(user.getUserStatus())
			.createdAt(user.getCreatedAt())
			.requestTaskCount(user.getRequestTaskCount())
			.creator(user.getCreator())
			.deposit(user.getDeposit())
			.lastLoginDate(user.getLastLoginDate())
			.build();
	}
}
