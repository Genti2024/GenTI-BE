package com.gt.genti.user.service;

import static com.gt.genti.user.service.validator.UserValidator.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.deposit.service.DepositService;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.picture.completed.repository.PictureCompletedRepository;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.picture.service.PictureService;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.request.UserInfoUpdateRequestDto;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.dto.request.UserStatusUpdateRequestDto;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.dto.response.UserFindByAdminResponseDto;
import com.gt.genti.user.dto.response.UserFindResponseDto;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.social.SocialLoginContext;

import jakarta.validation.constraints.NotNull;
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
	private final SocialLoginContext socialLoginContext;
	private final JwtTokenProvider jwtTokenProvider;

	public SocialLoginResponse login(final SocialLoginRequest request) {
		if (socialLoginContext.support(request.oauthPlatform())) {
			return socialLoginContext.doLogin(request);
		}
		throw ExpectedException.withLogging(ResponseCode.OauthProviderNotAllowed);
	}

	public Boolean logout(final Long userId) {
		User foundUser = getUserByUserId(userId);
		validateUserAuthorization(foundUser.getId(), userId);
		jwtTokenProvider.deleteRefreshToken(userId);
		return true;
	}

	@Transactional
	public UserFindResponseDto getUserInfo(Long userId) {
		User foundUser = getUserByUserId(userId);
		List<CommonPictureResponseDto> profilePictureResponseList = null;
		if (!foundUser.getPictureProfileList().isEmpty()) {
			profilePictureResponseList = foundUser.getPictureProfileList()
				.stream()
				.map(CommonPictureResponseDto::of)
				.toList();
		}

		return UserFindResponseDto.of(foundUser);
	}

	@Transactional
	public UserFindResponseDto updateUserInfo(Long userId, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User foundUser = getUserByUserId(userId);
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
				.map(CommonPictureResponseDto::of)
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
		User foundUser = getUserByUserId(userId);
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
	public Boolean deleteUserSoft(Long userId) {
		User foundUser = getUserByUserId(userId);
		foundUser.softDelete();
		return true;
	}

	@Transactional
	public Boolean restoreSoftDeletedUser(Long userId) {
		User foundUser = getUserByUserId(userId);
		foundUser.restore();
		return true;
	}

	@Transactional
	public Boolean updateUserStatus(Long userId, UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
		User foundUser = getUserByUserId(userId);
		foundUser.updateStatus(userStatusUpdateRequestDto.getUserStatus());
		return true;
	}

	public Page<CommonPictureResponseDto> getAllMyGeneratedPicture(Long userId, Pageable pageable) {
		User foundUser = getUserByUserId(userId);
		return pictureCompletedRepository.findAllByUserPagination(foundUser, pageable)
			.map(CommonPictureResponseDto::of);
	}

	public Page<UserFindByAdminResponseDto> getAllUserInfo(Pageable pageable) {
		return userRepository.findAll(pageable).map(mapToUserFindByAdminResponseDto());
	}

	public Page<UserFindByAdminResponseDto> getAllUserInfoByUserRole(UserRole userRole, Pageable pageable) {
		return userRepository.findAllByUserRole(pageable, userRole)
			.map(mapToUserFindByAdminResponseDto());
	}

	private User getUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId.toString()));
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

	public void softDeleteUser(LocalDateTime currentDate) {
		List<User> expiredUserList = userRepository.findIdByDeletedAtBefore(currentDate);
		if (!expiredUserList.isEmpty()) {
			userRepository.deleteAllInBatch(expiredUserList);
		}
	}
}
