package com.gt.genti.application.service;

import static com.gt.genti.other.util.TimeUtils.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.CreatePictureCompletedCommand;
import com.gt.genti.command.CreatePictureCreatedByCreatorCommand;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.admin.response.PGRESUpdateByAdminResponseDto;
import com.gt.genti.dto.common.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.dto.creator.request.MemoUpdateRequestDto;
import com.gt.genti.dto.creator.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.dto.creator.response.PGRESUpdateByCreatorResponseDto;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.util.TimeUtils;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.DepositRepository;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.SettlementRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateWorkService {
	private final PictureService pictureService;
	private final CreatorRepository creatorRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final SettlementRepository settlementRepository;
	private final DepositRepository depositRepository;
	private final RequestMatchService requestMatchService;

	public PGREQBriefFindByCreatorResponseDto getPictureGenerateRequestBrief(User user,
		PictureGenerateRequestStatus status) {
		Creator foundCreator = findCreatorByUser(user);
		Optional<PictureGenerateRequest> foundPGR;
		switch (status) {
			case IN_PROGRESS ->
				foundPGR = pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
					foundCreator);
			case ASSIGNING ->
				foundPGR = pictureGenerateRequestRepository.findByStatusOrderByCreatedAtDesc(foundCreator, status);
			default -> throw ExpectedException.withLogging(DomainErrorCode.NotSupportedTemp);
		}

		return foundPGR.map(PGREQBriefFindByCreatorResponseDto::new)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureGenerateRequestNotFound));

	}

	public PGREQDetailFindResponseDto getPictureGenerateRequestDetail(User user,
		Long pictureGenerateRequestId) {
		Creator foundCreator = findCreatorByUser(user);
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (foundPictureGenerateRequest.getCreator() == null) {
			throw ExpectedException.withLogging(DomainErrorCode.NotMatchedYet);
		} else if (!Objects.equals(foundPictureGenerateRequest.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(DomainErrorCode.NotAssignedToMe);
		}

		return new PGREQDetailFindResponseDto(foundPictureGenerateRequest);

	}

	public List<PGREQDetailFindResponseDto> getPictureGenerateRequestDetailAll(User user) {
		Creator foundCreator = findCreatorByUser(user);
		List<PictureGenerateRequest> foundPGRList = pictureGenerateRequestRepository.findAllByCreatorIsOrderByCreatedAtDesc(
			foundCreator);

		return foundPGRList.stream().map(
			PGREQDetailFindResponseDto::new).toList();

	}

	@Transactional
	public Boolean updatePictureCreatedByCreatorList(Long pictureGenerateResponseId,
		List<CommonPictureKeyUpdateRequestDto> commonPictureKeyUpdateRequestDtoList, User uploader) {

		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);

		List<CreatePictureCreatedByCreatorCommand> newUploadPictures = commonPictureKeyUpdateRequestDtoList.stream()
			.map(command -> (CreatePictureCreatedByCreatorCommand)CreatePictureCreatedByCreatorCommand.builder()
				.key(command.getKey())
				.uploader(uploader)
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.build()
			)
			.toList();

		pictureService.updateAll(newUploadPictures);
		return true;
	}

	@Transactional
	public Boolean updateMemo(Long pictureGenerateResponseId, MemoUpdateRequestDto memoUpdateRequestDto) {
		PictureGenerateResponse foundPictureGenerateResponse = findPGRES(pictureGenerateResponseId);
		foundPictureGenerateResponse.updateMemo(memoUpdateRequestDto.getMemo());
		return true;
	}

	public Boolean acceptPictureGenerateRequest(User user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser(), user)) {
			throw ExpectedException.withLogging(DomainErrorCode.NotAssignedToMe);
		}
		foundPictureGenerateRequest.accept();
		return true;
	}

	private PictureGenerateRequest findPGREQ(Long pictureGenerateRequestId) {
		return pictureGenerateRequestRepository.findById(
				pictureGenerateRequestId)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureGenerateRequestNotFound));
	}

	@Transactional
	public Boolean rejectPictureGenerateRequest(User user, Long pictureGenerateRequestId) {
		PictureGenerateRequest foundPictureGenerateRequest = findPGREQ(pictureGenerateRequestId);
		if (!Objects.equals(foundPictureGenerateRequest.getCreator().getUser(), user)) {
			throw ExpectedException.withLogging(DomainErrorCode.NotAssignedToMe);
		}

		foundPictureGenerateRequest.reject();
		requestMatchService.matchRejectedRequest(foundPictureGenerateRequest);
		return true;
	}

	@Transactional
	public PGRESUpdateByCreatorResponseDto submitToAdmin(User user, Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		Creator foundCreator = findCreatorByUser(user);

		if (!Objects.equals(foundPGRES.getCreator().getId(), foundCreator.getId())) {
			throw ExpectedException.withLogging(DomainErrorCode.NotAssignedToMe);
		}

		pictureService.findPictureCreatedByCreatorByPictureGenerateResponse(foundPGRES);

		foundPGRES.creatorSubmit();

		Duration elapsedDuration = foundPGRES.getElapsedTime();

		if (elapsedDuration.compareTo(Duration.ofHours(TimeUtils.PGRES_LIMIT_HOUR)) > 0) {
			throw ExpectedException.withLogging(DomainErrorCode.ExpiredPictureGenerateRequest);
		}

		Long reword = calculateReward(elapsedDuration.toMinutes());

		Settlement settlement = Settlement.builder()
			.pictureGenerateResponse(foundPGRES)
			.elapsed(elapsedDuration)
			.reward(reword).build();

		settlementRepository.save(settlement);
		Deposit foundDeposit = depositRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.DepositNotFound));

		foundDeposit.add(reword);

		return PGRESUpdateByCreatorResponseDto.builder()
			.elapsedTime(getTimeString(elapsedDuration))
			.build();
	}

	@Transactional
	public PGRESUpdateByAdminResponseDto submitFinal(Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPGRES = findPGRES(pictureGenerateResponseId);
		List<PictureCompleted> pictureCompletedList = pictureService.findAllPictureCompletedByPictureGenerateResponse(
			foundPGRES);
		if (pictureCompletedList.isEmpty()) {
			throw ExpectedException.withLogging(DomainErrorCode.FinalPictureNotUploadedYet);
		}
		foundPGRES.adminSubmit();
		Duration elapsedDuration = foundPGRES.getElapsedTime();

		//TODO 어드민은 시간 체크 할 필요 없겠지?
		// edited at 2024-05-20
		// author 서병렬

		//TODO 요청자에게 앱 푸시알림
		// edited at 2024-05-21
		// author 서병렬

		return PGRESUpdateByAdminResponseDto.builder()
			.elapsedTime(getTimeString(elapsedDuration))
			.build();
	}

	public Boolean updatePictureListCreatedByAdmin(User uploader,
		List<CommonPictureKeyUpdateRequestDto> requestDtoList,
		Long pictureGenerateResponseId) {
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(
				DomainErrorCode.PictureGenerateResponseNotFound));

		List<CreatePictureCompletedCommand> commandList = requestDtoList.stream().map(
			dto -> (CreatePictureCompletedCommand)CreatePictureCompletedCommand.builder()
				.pictureGenerateResponse(foundPictureGenerateResponse)
				.key(dto.getKey())
				.uploader(uploader).build()
		).toList();
		pictureService.updatePictures(commandList);
		return true;
	}

	public List<PGREQDetailFindResponseDto> getPictureGenerateRequestDetail3(User user) {
		Creator foundCreator = findCreatorByUser(user);
		List<PictureGenerateRequestStatus> activeRequestStatusList = new ArrayList<>();
		activeRequestStatusList.add(PictureGenerateRequestStatus.IN_PROGRESS);

		List<PictureGenerateResponseStatus> activeResponseStatusList = new ArrayList<>();
		activeResponseStatusList.add(PictureGenerateResponseStatus.BEFORE_WORK);
		List<PictureGenerateRequest> foundPGREQList = pictureGenerateRequestRepository.findByCreatorAndActiveStatus(
			foundCreator, activeRequestStatusList, activeResponseStatusList);

		return foundPGREQList.stream().map(PGREQDetailFindResponseDto::new).toList();
	}

	private PictureGenerateResponse findPGRES(Long pictureGenerateResponseId) {
		return pictureGenerateResponseRepository.findById(
				pictureGenerateResponseId)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.PictureGenerateResponseNotFound));
	}

	private Creator findCreatorByUser(User user) {
		return creatorRepository.findByUser(user)
			.orElseThrow(() -> ExpectedException.withLogging(DomainErrorCode.CreatorNotFound));
	}
}

