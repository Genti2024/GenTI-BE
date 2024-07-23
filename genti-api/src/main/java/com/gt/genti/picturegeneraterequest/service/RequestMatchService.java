package com.gt.genti.picturegeneraterequest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.common.AdminService;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.matchingstrategy.model.RequestMatchStrategy;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestMatchService {
	private final CreatorRepository creatorRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final AdminService adminService;
	private final MatchEventPublisher matchEventPublisher;

	@Getter
	private RequestMatchStrategy currentStrategy = RequestMatchStrategy.ADMIN_ONLY;

	public RequestMatchStrategy changeMatchingStrategy(RequestMatchStrategy strategy) {
		this.currentStrategy = strategy;
		return currentStrategy;
	}

	@Transactional
	public void matchIfNotMatchedPGREQExists() {
		List<PictureGenerateRequest> pendingRequestList = pictureGenerateRequestRepository.findPendingRequests();
		List<Creator> creatorList = getAvailableCreators();
		GentiMatchResult gentiMatchResult = new GentiMatchResult(currentStrategy);

		if (pendingRequestList.isEmpty()) {
			log.info("매칭 대기중인 작업이 없음");
			return;
		}

		matchRequestsWithStrategy(pendingRequestList, creatorList, gentiMatchResult);
	}

	private void matchRequestsWithStrategy(List<PictureGenerateRequest> requestList, List<Creator> availableCreatorList,
		GentiMatchResult gentiMatchResult) {
		switch (currentStrategy) {
			case ADMIN_ONLY -> matchAllToAdmin(requestList, gentiMatchResult);
			case CREATOR_ADMIN -> matchToCreatorOrAdmin(requestList, availableCreatorList, gentiMatchResult);
			case CREATOR_ONLY -> {
				if (availableCreatorList.isEmpty()) {
					gentiMatchResult.addSummary("%d개의 매칭 대기중인 작업 || 작업 가능한 작업자가 없음".formatted(requestList.size()));
				} else {
					matchToCreatorsOnly(requestList, availableCreatorList, gentiMatchResult);
				}
			}
		}
		logAndPublishEvent(gentiMatchResult);
	}

	@Transactional
	public void matchNewRequest(PictureGenerateRequest pictureGenerateRequest) {
		matchSingleRequest(pictureGenerateRequest, "신규 요청에 대하여 매칭 시도");
	}

	@Transactional
	public void matchRejectedRequest(PictureGenerateRequest pictureGenerateRequest) {
		matchSingleRequest(pictureGenerateRequest, "거절된 요청에 대해서 재 매칭 시도");
	}

	private void matchAllToAdmin(List<PictureGenerateRequest> pendingRequestList, GentiMatchResult gentiMatchResult) {
		Creator adminCreator = adminService.getAdminCreator();
		List<PictureGenerateResponse> pgresList = new ArrayList<>();

		pendingRequestList.forEach(pgr -> matchRequestToAdmin(pgr, adminCreator, pgresList, gentiMatchResult));

		pictureGenerateResponseRepository.saveAll(pgresList);
		gentiMatchResult.addSummary("대기중이던 요청 %d개 전부를 어드민에게 매칭함".formatted(pendingRequestList.size()));
	}

	private void matchToCreatorOrAdmin(List<PictureGenerateRequest> pendingRequestList, List<Creator> creatorList,
		GentiMatchResult gentiMatchResult) {
		if (creatorList.isEmpty()) {
			gentiMatchResult.addSummary("%d개의 매칭 대기중인 작업에 대해 작업 가능한 작업자가 없으므로 admin 매칭으로 전환"
				.formatted(pendingRequestList.size()));
			matchAllToAdmin(pendingRequestList, gentiMatchResult);
			return;
		}

		int matchableCount = Math.min(pendingRequestList.size(), creatorList.size());

		IntStream.range(0, matchableCount)
			.forEach(i -> matchRequestToCreator(pendingRequestList.get(i), creatorList.get(i), gentiMatchResult));

		if (pendingRequestList.size() > matchableCount) {
			matchRemainingRequestsToAdmin(pendingRequestList.subList(matchableCount, pendingRequestList.size()),
				gentiMatchResult);
		}

		gentiMatchResult.addSummary(
			"대기중이던 요청 %d개, 작업자에게 매칭된 요청 %d개".formatted(pendingRequestList.size(), matchableCount));
	}

	private void matchToCreatorsOnly(List<PictureGenerateRequest> pendingRequestList, List<Creator> creatorList,
		GentiMatchResult resultSB) {
		// TODO: CreatorOnly 전략 사용하기전 개발
	}

	private void matchRequestToAdmin(PictureGenerateRequest pgr, Creator adminCreator,
		List<PictureGenerateResponse> pgresList, GentiMatchResult gentiMatchResult) {
		pgr.assignToAdmin(adminCreator);
		PictureGenerateResponse newPGRES = PictureGenerateResponse.createAdminMatchedPGRES(adminCreator, pgr);
		pgr.addPGRES(newPGRES);
		adminCreator.addPictureGenerateRequest(pgr);
		adminCreator.addPictureGenerateResponse(newPGRES);
		pgresList.add(newPGRES);
		gentiMatchResult.addMatchResult(
			"email : %s가 요청한 id : %d 요청을 어드민에게 매칭".formatted(pgr.getRequester().getEmail(), pgr.getId()));
	}

	private void matchRequestToCreator(PictureGenerateRequest pgr, Creator creator, GentiMatchResult gentiMatchResult) {
		pgr.assignToCreator(creator);
		creator.addPictureGenerateRequest(pgr);
		gentiMatchResult.addMatchResult("email : %s가 요청한 id : %d 요청을 작업자 email : %s id : %d에게 매칭"
			.formatted(pgr.getRequester().getEmail(), pgr.getId(), creator.getUser().getEmail(), creator.getId()));
	}

	private void matchRemainingRequestsToAdmin(List<PictureGenerateRequest> remainingRequests,
		GentiMatchResult gentiMatchResult) {
		List<PictureGenerateResponse> pgresList = new ArrayList<>();
		Creator adminCreator = adminService.getAdminCreator();
		remainingRequests.forEach(pgr -> matchRequestToAdmin(pgr, adminCreator, pgresList, gentiMatchResult));
		pictureGenerateResponseRepository.saveAll(pgresList);
		gentiMatchResult.addMatchResult("어드민에게 매칭된 요청 %d개 ->  모든 남은 요청이 매칭됨".formatted(remainingRequests.size()));
	}

	private void matchSingleRequest(PictureGenerateRequest pictureGenerateRequest, String summary) {
		GentiMatchResult gentiMatchResult = new GentiMatchResult(currentStrategy);
		gentiMatchResult.addSummary(summary);
		List<Creator> availableCreatorList = getAvailableCreators();
		List<PictureGenerateRequest> justOneRequest = List.of(pictureGenerateRequest);
		matchRequestsWithStrategy(justOneRequest, availableCreatorList, gentiMatchResult);
	}

	private List<Creator> getAvailableCreators() {
		List<Creator> allCreator = creatorRepository.findAllAvailableCreator();
		Creator adminCreator = adminService.getAdminCreator();
		if (adminCreator == null) {
			return allCreator;
		}
		return allCreator.stream()
			.filter(creator -> !creator.getId().equals(adminCreator.getId()))
			.collect(Collectors.toList());
	}

	private void logAndPublishEvent(GentiMatchResult gentiMatchResult) {
		log.info(gentiMatchResult.toString());
		matchEventPublisher.publishSignUpEvent(gentiMatchResult);
	}
}

