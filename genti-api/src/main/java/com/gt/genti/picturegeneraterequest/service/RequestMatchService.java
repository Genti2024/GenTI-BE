package com.gt.genti.picturegeneraterequest.service;

import java.util.ArrayList;
import java.util.List;
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

	public static RequestMatchStrategy CURRENT_STRATEGY = RequestMatchStrategy.ADMIN_ONLY;

	public static RequestMatchStrategy changeMatchingStrategy(RequestMatchStrategy strategy) {
		CURRENT_STRATEGY = strategy;
		return CURRENT_STRATEGY;
	}

	@Transactional
	public void matchPictureGenerateRequests() {
		StringBuilder resultSB = new StringBuilder();
		resultSB.append("""
			[%s] 전략으로 자동매칭 시작
			""".formatted(CURRENT_STRATEGY.getStringValue())).append('\n');

		List<PictureGenerateRequest> pendingRequestList = pictureGenerateRequestRepository.findPendingRequests();
		List<Creator> creatorList = getCreatorListExceptAdmin();

		matchRequest(pendingRequestList, creatorList, resultSB);
	}

	@Transactional
	public void matchNewRequest(PictureGenerateRequest pictureGenerateRequest) {
		StringBuilder resultSB = new StringBuilder();
		resultSB.append("신규 요청에 대하여 매칭 시도");
		matchRequest(pictureGenerateRequest, resultSB);
	}

	@Transactional
	public void matchRejectedRequest(PictureGenerateRequest pictureGenerateRequest) {
		StringBuilder resultSB = new StringBuilder();
		resultSB.append("거절된 요청에 대해서 재 매칭 시도");
		matchRequest(pictureGenerateRequest, resultSB);
	}

	private void matchRequestToAdmin(List<PictureGenerateRequest> pendingRequestList, StringBuilder resultSB) {
		Creator adminCreator = adminService.getAdminCreator();
		int requestCount = pendingRequestList.size();
		List<String> matchResultList = new ArrayList<>();
		List<PictureGenerateResponse> pgresList = new ArrayList<>();
		pendingRequestList.forEach(pgr -> {
			pgr.assignToAdmin(adminCreator);
			pgresList.add(PictureGenerateResponse.createAdminMatchedPGRES(adminCreator, pgr));
			String result = """
				email : [%s] 가 요청한 id : [%d] 요청을 어드민에게 매칭
				  """.formatted(pgr.getRequester().getEmail(), pgr.getId());
			matchResultList.add(result);
		});

		pictureGenerateResponseRepository.saveAll(pgresList);

		String result = """
			대기중이던 요청 [%d]개 전부를 어드민에게 매칭함
			""".formatted(requestCount);
		matchResultList.add(result);

		// discordController.sendToAdminChannel(String.join("\n", matchResultList));
	}

	private void matchRequestCreatorAdmin(List<PictureGenerateRequest> pendingRequestList,
		List<Creator> availableCreatorList, StringBuilder resultSB) {
		int requestCount = pendingRequestList.size();
		int creatorCount = availableCreatorList.size();
		int matchableCount = Math.min(requestCount, creatorCount);

		IntStream.range(0, matchableCount).forEach(i -> {
				Creator creator = availableCreatorList.get(i);
				PictureGenerateRequest pgr = pendingRequestList.get(i);
				pgr.assignToCreator(creator);
				String result = """
					email : [%s]가 요청한 id : [%d] 요청을 작업자 email : [%s] id : [%d]에게 매칭
					  """.formatted(pgr.getRequester().getEmail(), pgr.getId(), creator.getUser().getEmail(),
					creator.getId());
				resultSB.append(result).append('\n');
			}
		);

		int remainRequestCount = requestCount - matchableCount;
		int notMatchedIndex = matchableCount;
		if (remainRequestCount > 0) {
			Creator adminCreator = adminService.getAdminCreator();
			while (notMatchedIndex < pendingRequestList.size()) {
				PictureGenerateRequest currentRequest = pendingRequestList.get(notMatchedIndex);
				currentRequest.assignToAdmin(adminCreator);
				notMatchedIndex++;
				String result = """
					요청 id : [%d]에 대하여 작업 가능한 작업자가 없음 어드민에게 매칭
					""".formatted(currentRequest.getId());
				resultSB.append(result).append('\n');
			}
		}
		String creatorMatchResult = """
			대기중이던 요청 [%d]개, 작업자에게 매칭된 요청 [%d]개
			""".formatted(requestCount, availableCreatorList.size());
		resultSB.append(creatorMatchResult);
		// discordController.sendToEventChannel(print(resultSB));

		String adminMatchResult = """
			 어드민에게 매칭된 요청 [%d]개 -> \n 모든 남은 요청이 매칭됨
			""".formatted(requestCount - (notMatchedIndex + 1));
		// discordController.sendToAdminChannel(adminMatchResult);
	}

	private void matchRequest(List<PictureGenerateRequest> pendingRequestList, List<Creator> creatorList,
		StringBuilder resultSB) {

		// 현재 작업중인 요청의 수가 작은 기준으로 조회되었음
		switch (CURRENT_STRATEGY) {
			case ADMIN_ONLY -> {
				if (pendingRequestList.isEmpty()) {
					resultSB.append("매칭 대기중인 작업이 없음");
					// discordController.sendToEventChannel(print(resultSB));
					return;
				}

				matchRequestToAdmin(pendingRequestList, resultSB);
			}
			case CREATOR_ADMIN -> {
				if (creatorList.isEmpty()) {
					resultSB.append("""
						[%d]개의 매칭 대기중인 작업에 대해 작업 가능한 작업자가 없으므로 admin 매칭으로 전환"""
						.formatted(pendingRequestList.size()));
					matchRequestToAdmin(pendingRequestList, resultSB);
				}
				matchRequestCreatorAdmin(pendingRequestList, creatorList, resultSB);
			}
			case CREATOR_ONLY -> {
				//TODO Creator가 많아지면 개발
				// edited at 2024-05-25
				// author 서병렬
				if (creatorList.isEmpty()) {
					resultSB.append("""
						[%d]개의 매칭 대기중인 작업 || 작업 가능한 작업자가 없음""".formatted(pendingRequestList.size()));
					// discordController.sendToEventChannel(print(resultSB));
					return;
				}
				/* dosomething */
			}
		}

	}

	private void matchRequest(PictureGenerateRequest pictureGenerateRequest, StringBuilder resultSB) {
		List<Creator> availableCreatorList = getCreatorListExceptAdmin();
		List<PictureGenerateRequest> justOneRequest = List.of(pictureGenerateRequest);
		matchRequest(justOneRequest, availableCreatorList, resultSB);
	}

	private void pushToCreator(Creator creator) {
		//TODO 공급자 앱에 푸시알림
		// edited at 2024-05-04
		// author
	}

	private String print(StringBuilder sb) {
		String result = sb.toString();
		sb.setLength(0);
		return result;
	}

	private List<Creator> getCreatorListExceptAdmin() {
		List<Creator> allCreator = creatorRepository.findAllAvailableCreator();
		Creator adminCreator = adminService.getAdminCreator();
		allCreator.removeIf(creator -> creator.getId().equals(adminCreator.getId()));
		return allCreator;
	}
}
