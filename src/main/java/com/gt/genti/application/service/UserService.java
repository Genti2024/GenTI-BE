package com.gt.genti.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.dto.ChangeUserRoleDto;
import com.gt.genti.dto.ChangeUserStatusDto;
import com.gt.genti.dto.CommonPictureResponseDto;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.PictureCompletedRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final DepositService depositService;
	private final CreatorRepository creatorRepository;
	private final PictureCompletedRepository pictureCompletedRepository;

	@Transactional
	public UserInfoResponseDto getUserInfo(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		PictureProfile pictureProfile = foundUser.getPictureProfile();
		return UserInfoResponseDto.builder()
			.user(foundUser)
			.pictureProfile(pictureProfile)
			.build();
	}

	@Transactional
	public UserInfoResponseDto updateUserInfo(Long userId, UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		User findUser = findActivateUserByUserId(userId);

		findUser.update(userInfoUpdateRequestDto);
		return UserInfoResponseDto.builder()
			.user(findUser)
			.pictureProfile(findUser.getPictureProfile())
			.build();
	}

	@Transactional
	public Boolean deleteUserInfoSoft(Long userId) {
		User findUser = findActivateUserByUserId(userId);
		findUser.softDelete();
		return true;
	}

	@Transactional
	public Boolean restoreSoftDeletedUser(Long userId) {
		User findUser = findDeactivatedUserByUserId(userId);
		findUser.restore();
		return true;
	}

	@Transactional
	public Boolean updateUserStatus(Long userId, ChangeUserStatusDto changeUserStatusDto) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.updateStatus(changeUserStatusDto.getUserStatus());
		return true;
	}

	@Transactional
	public Boolean updateUserRole(Long userId, ChangeUserRoleDto changeUserRoleDto) {
		User foundUser = findActivateUserByUserId(userId);
		foundUser.updateUserRole(changeUserRoleDto.getUserRole());
		Creator newCreator = new Creator(foundUser);
		creatorRepository.save(newCreator);
		depositService.createDeposit(foundUser);
		return true;
	}

	public List<CommonPictureResponseDto> getAllMyGeneratedPicture(Long userId) {
		User foundUser = findActivateUserByUserId(userId);
		log.info("userId" + userId);
		List<PictureCompleted> pictureCompletedList = pictureCompletedRepository.findAllByUser(foundUser);
		return pictureCompletedList.stream()
			.map(entity -> new CommonPictureResponseDto(entity.getId(), entity.getUrl()))
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
}
