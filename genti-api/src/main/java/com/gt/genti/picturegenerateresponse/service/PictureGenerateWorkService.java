package com.gt.genti.picturegenerateresponse.service;

import static com.gt.genti.common.EnumUtil.*;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.completed.repository.PictureCompletedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.command.CreatePictureCompletedCommand;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picture.service.PictureService;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegenerateresponse.dto.request.PGRESUpdateAdminInChargeRequestDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESSubmitByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESUpdateAdminInChargeResponseDto;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.util.DateTimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureGenerateWorkService {
	private final PictureService pictureService;
	private final CreatorRepository creatorRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final PictureCompletedRepository pictureCompletedRepository;
	private final UserRepository userRepository;
 	private final PGRESCompleteEventPublisher PGRESCompleteEventPublisher;

	public PGRESSubmitByAdminResponseDto submitFinal(Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		List<PictureCompleted> pictureCompletedList = pictureService.findAllPictureCompletedByPictureGenerateResponse(
			foundPGRES);
		if (pictureCompletedList.isEmpty()) {
			throw ExpectedException.withLogging(ResponseCode.FinalPictureNotUploadedYet, pictureGenerateResponseId);
		}
		foundPGRES.adminSubmit();
		Duration elapsedDuration = foundPGRES.getAdminElapsedTime();

		PGRESCompleteEventPublisher.publishPictureGenerateCompleteEvent(foundPGRES.getRequest().getRequester().getId());

		return PGRESSubmitByAdminResponseDto.builder()
			.pictureGenerateResponseId(foundPGRES.getId())
			.elapsedTime(DateTimeUtil.getTimeString(elapsedDuration))
			.build();
	}

	public List<CommonPictureResponseDto> updatePictureListCreatedByAdmin(Long userId,
		List<CommonPictureKeyUpdateRequestDto> requestDtoList,
		Long pictureGenerateResponseId) {

		User foundUser = findUserById(userId);
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(
				ResponseCode.PictureGenerateResponseNotFound));

		if (PictureGenerateResponseStatus.COMPLETED.equals(foundPGRES.getStatus())) {
			throw ExpectedException.withLogging(ResponseCode.AlreadyCompletedPictureGenerateResponse);
		}

		List<CreatePictureCompletedCommand> commandList = requestDtoList.stream().map(
			dto -> (CreatePictureCompletedCommand)CreatePictureCompletedCommand.builder()
				.pictureGenerateResponse(foundPGRES)
				.key(dto.getKey())
				.requester(foundPGRES.getRequest().getRequester())
				.uploader(foundUser)
				.pictureRatio(foundPGRES.getRequest().getPictureRatio())
				.build()
		).toList();
		return pictureService.updatePictures(commandList)
			.stream().map(CommonPictureResponseDto::of)
			.toList();
	}

	public Boolean verifyPGRES(Long userId, Long pgresId) {
		User foundUser = findUserById(userId);
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(pgresId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateResponseNotFound));
		PictureGenerateRequest pgreq = foundPGRES.getRequest();
		if (!Objects.equals(pgreq.getRequester().getId(), foundUser.getId())) {
			throw ExpectedException.withLogging(ResponseCode.OnlyRequesterCanViewPictureGenerateRequest);
		}
		if (!pgreq.getPictureGenerateRequestStatus().equals(PictureGenerateRequestStatus.AWAIT_USER_VERIFICATION)) {
			throw ExpectedException.withLogging(ResponseCode.UnexpectedPictureGenerateRequestStatus,
				pgreq.getPictureGenerateRequestStatus().getResponse());
		}
		pgreq.userVerified();
		foundPGRES.userVerified();
		return true;
	}

	public PGRESUpdateAdminInChargeResponseDto updateAdminInCharge(Long pgresId,
		PGRESUpdateAdminInChargeRequestDto requestDto) {
		PictureGenerateResponse foundPGRES = findPGRES(pgresId);

		if (!PGRES_CAN_CHANGE_ADMIN_IN_CHARGE_LIST.contains(foundPGRES.getStatus())) {
			throw ExpectedException.withLogging(ResponseCode.RequestBlockedDueToPictureGenerateResponseStatus,
				foundPGRES.getStatus().getResponse());
		}
		foundPGRES.updateInChargeAdmin(requestDto.getAdminInCharge());
		pictureGenerateResponseRepository.save(foundPGRES);

		return PGRESUpdateAdminInChargeResponseDto.builder()
			.pictureGenerateResponseId(foundPGRES.getId())
			.adminInCharge(foundPGRES.getAdminInCharge())
			.status(foundPGRES.getStatus())
			.build();
	}

	public Boolean ratePicture(Long userId, Long pgresId, Integer star) {
		User foundUser = findUserById(userId);
		PictureGenerateResponse foundPGRES = pictureGenerateResponseRepository.findById(pgresId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateResponseNotFound));
		PictureGenerateRequest pgreq = foundPGRES.getRequest();
		if (!Objects.equals(pgreq.getRequester().getId(), foundUser.getId())) {
			throw ExpectedException.withLogging(ResponseCode.OnlyRequesterCanViewPictureGenerateRequest);
		}
		if (!pgreq.getPictureGenerateRequestStatus().equals(PictureGenerateRequestStatus.AWAIT_USER_VERIFICATION)) {
			throw ExpectedException.withLogging(ResponseCode.UnexpectedPictureGenerateRequestStatus,
				pgreq.getPictureGenerateRequestStatus().getResponse());
		}
		pgreq.userVerified();
		foundPGRES.userVerified();
		foundPGRES.updateStar(star);

		return true;
	}

	public void expire(PictureGenerateResponse pictureGenerateResponse) {
		pictureGenerateResponse.expired();
		//TODO 이 사진생성응답의 작업자(공급자) 를 3일 비활성화 하기
		// edited at 2024-07-19
		// author 서병렬

	}

	public Boolean finishPGRESByFrontend(Long userId){
		User foundUser = findUserById(userId);
		Optional<PictureGenerateRequest> pgreq = pictureGenerateRequestRepository.findTop1ByRequesterOrderByIdDesc(foundUser);
		if (pgreq.isPresent()){
			PictureGenerateRequest foundpgreq = pgreq.get();
			if(foundpgreq.getPictureGenerateRequestStatus().equals(PictureGenerateRequestStatus.MATCH_TO_ADMIN)){
				Optional<PictureGenerateResponse> pgres = pictureGenerateResponseRepository.findByRequest(pgreq.get());
				if(pgres.isPresent()) {
					PictureGenerateResponse foundpgres = pgres.get();
					String key = switch (foundpgreq.getPictureRatio()) {
                        case PictureRatio.RATIO_GARO -> "DEV/ADMIN_UPLOADED_IMAGE/3.png";
                        case PictureRatio.RATIO_SERO -> "DEV/ADMIN_UPLOADED_IMAGE/1.png";
                        default -> "";
                    };
                    PictureCompleted pictureCompleted = PictureCompleted.builder()
							.key(key)
							.pictureGenerateResponse(foundpgres)
							.uploadedBy(foundUser)
							.requester(foundUser)
							.pictureRatio(foundpgreq.getPictureRatio())
							.build();
					pictureCompletedRepository.save(pictureCompleted);
					foundpgres.adminSubmit();
					return true;
				}
			}
		}
		return false;
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
	}

	private PictureGenerateRequest findPGREQ(Long pictureGenerateRequestId) {
		return pictureGenerateRequestRepository.findById(
				pictureGenerateRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateRequestNotFound,
				String.format("사진생성요청 Id : %d", pictureGenerateRequestId)));
	}

	private PictureGenerateResponse findPGRES(Long pictureGenerateResponseId) {
		return pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureGenerateResponseNotFound));
	}

	private Creator findCreatorByUserId(Long userId) {
		return creatorRepository.findByUserId(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.CreatorNotFound, userId));
	}
}

