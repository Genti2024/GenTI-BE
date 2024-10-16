package com.gt.genti.user.service;

import static com.gt.genti.picturegeneraterequest.service.PictureGenerateRequestCancellationReason.*;
import static com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.auth.dto.request.SignUpRequestDTO;
import com.gt.genti.auth.social.AppleOauthStrategy;
import com.gt.genti.auth.social.KakaoOauthStrategy;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.deposit.service.DepositService;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picture.completed.repository.PictureCompletedRepository;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.picture.service.PictureService;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.user.dto.request.UserInfoUpdateRequestDto;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.dto.request.UserStatusUpdateRequestDto;
import com.gt.genti.user.dto.response.SignUpResponseDTO;
import com.gt.genti.user.dto.response.UserFindByAdminResponseDto;
import com.gt.genti.user.dto.response.UserFindResponseDto;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.repository.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final PictureService pictureService;
	private final UserRepository userRepository;
	private final DepositService depositService;
	private final CreatorRepository creatorRepository;
	private final PictureCompletedRepository pictureCompletedRepository;
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final JwtTokenProvider jwtTokenProvider;

	private final KakaoOauthStrategy kakaoOauthStrategy;
	private final AppleOauthStrategy appleOauthStrategy;

	public UserFindResponseDto getUserInfo(Long userId) {
		User foundUser = getUserByUserId(userId);
		return UserFindResponseDto.of(foundUser);
	}

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

	public Boolean updateUserRole(Long userId, UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
		User foundUser = getUserByUserId(userId);
		UserRole userRole = userRoleUpdateRequestDto.getUserRole();
		foundUser.updateUserRole(userRole);
		if (userRole == UserRole.CREATOR) {
			Creator newCreator = new Creator(foundUser);
			creatorRepository.save(newCreator);
			foundUser.setCreator(newCreator);
			depositService.createDeposit(foundUser);
		} else if (userRole == UserRole.ADMIN) {
			Creator newCreator = new Creator(foundUser);
			foundUser.setCreator(newCreator);
			creatorRepository.save(newCreator);
		}
		return true;
	}

	public Boolean deleteUserSoft(Long userId) {
		User foundUser = getUserByUserId(userId);
		foundUser.softDelete();
		List<PictureGenerateResponse> deleteList = new ArrayList<>();
		if (foundUser.getCreator() != null) {
			Creator foundCreator = foundUser.getCreator();
			List<PictureGenerateResponse> pgresList = foundCreator.getPictureGenerateResponseList();
			foundCreator.getPictureGenerateRequestList()
				.stream()
				.filter(pgreq -> pgreq.getPictureGenerateRequestStatus().equals(
					PictureGenerateRequestStatus.IN_PROGRESS) && (pgreq.getResponseList().isEmpty()))
				.forEach(req -> pictureGenerateRequestUseCase.cancelRequest(req, SUPPLIER_EXIT));
			if (pgresList.isEmpty()) {
				return true;
			}

			pgresList.stream()
				.filter(pgres -> CREATOR_BEFORE_WORK.equals(pgres.getStatus()))
				.forEach(pgres -> {

					pictureGenerateRequestUseCase.cancelRequest(pgres.getRequest(), SUPPLIER_EXIT);
					pgres.clearRelationshipsWithPGREQ();
					deleteList.add(pgres);
				});
			pictureGenerateResponseRepository.deleteAll(deleteList);
		}
		return true;
	}

	public Boolean restoreSoftDeletedUser(Long userId) {
		User foundUser = getUserByUserId(userId);
		foundUser.restore();
		return true;
	}

	public Boolean delete(Long userId) {
		User foundUser = getUserByUserId(userId);
		OauthPlatform oauthPlatform = foundUser.getLastLoginOauthPlatform();

		switch (oauthPlatform) {
			case KAKAO -> kakaoOauthStrategy.unlink(foundUser.getSocialId());
			case APPLE -> appleOauthStrategy.unlink(foundUser.getAppleRefreshToken());
		}
		userRepository.delete(foundUser);
		return true;
	}

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

	public List<CommonPictureResponseDto> getAllMyGeneratedPictureNoPage(Long userId) {
		User foundUser = getUserByUserId(userId);
		List<PictureCompleted> pictures = pictureCompletedRepository.findAllByRequesterOrderByCreatedAtDesc(foundUser);
		return pictures.stream()
			.map(CommonPictureResponseDto::of)
			.collect(Collectors.toList());
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
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
	}

	@NotNull
	private Function<User, UserFindByAdminResponseDto> mapToUserFindByAdminResponseDto() {
		return user -> UserFindByAdminResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.userRole(user.getUserRole())
			.birthYear(user.getBirthYear())
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

	public Page<UserFindByAdminResponseDto> getUserInfoByEmail(String email) {
		User foundUser = userRepository.findByEmail(email)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFoundByEmail, email));
		UserFindByAdminResponseDto responseDto = UserFindByAdminResponseDto.builder()
			.email(foundUser.getEmail())
			.userRole(foundUser.getUserRole())
			.birthYear(foundUser.getBirthYear())
			.deposit(foundUser.getDeposit())
			.lastLoginDate(foundUser.getLastLoginDate())
			.userStatus(foundUser.getUserStatus())
			.requestTaskCount(foundUser.getRequestTaskCount())
			.creator(foundUser.getCreator())
			.id(foundUser.getId())
			.createdAt(foundUser.getCreatedAt())
			.lastLoginDate(foundUser.getLastLoginDate())
			.build();
		return new PageImpl<>(List.of(responseDto), PageRequest.of(0, 1), 1);
	}

	public SignUpResponseDTO signUp(Long userId, SignUpRequestDTO signUpRequestDTO) {
		User foundUser = getUserByUserId(userId);
		if (!foundUser.isFirstJoinUser()) {
			throw ExpectedException.withLogging(ResponseCode.UserAlreadySignedUp);
		}
		foundUser.updateBirthAndSex(signUpRequestDTO.getBirthYear(), signUpRequestDTO.getSex());
		foundUser.updateUserRole(UserRole.USER);

		return SignUpResponseDTO.builder()
			.email(foundUser.getEmail())
			.lastLoginOauthPlatform(foundUser.getLastLoginOauthPlatform())
			.nickname(foundUser.getNickname())
			.birthYear(foundUser.getBirthYear())
			.sex(foundUser.getSex())
			.build();
	}

	public Boolean logout(final Long userId) {
		return jwtTokenProvider.deleteRefreshToken(userId);
	}

}
