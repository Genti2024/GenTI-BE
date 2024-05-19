package com.gt.genti.application.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.external.discord.service.DiscordService;
import com.gt.genti.other.util.RandomUtils;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.PictureGenerateRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestMatchService {
	private final CreatorRepository creatorRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final DiscordService discordService;
	private final MatchingRegistry matchingRegistry;

	@Transactional
	public void matchPictureGenerateRequests() {
		List<PictureGenerateRequest> pendingRequestList = pictureGenerateRequestRepository.findPendingRequests();
		if (pendingRequestList.isEmpty()) {
			discordService.sendToDiscord("매칭 대기중인 작업이 없음");
			return;
		}

		List<Creator> availableCreatorList = creatorRepository.findAllAvailableCreator();
		if (availableCreatorList.isEmpty()) {
			String message = """
				[%d]개의 매칭 대기중인 작업 || 작업 가능한 작업자가 없음""".formatted(pendingRequestList.size());
			discordService.sendToDiscord(message);
			return;
		}

		int size = Math.min(pendingRequestList.size(), availableCreatorList.size());
		String[] matchResultArray = new String[size + 1];
		IntStream.range(0, size).forEach(i -> {
				Creator creator = availableCreatorList.get(i);
				PictureGenerateRequest pgr = pendingRequestList.get(i);
				pgr.assign(creator);
				matchResultArray[i] = """
					email : [%s] id : [%d] 요청을 작업자 email : [%s] id : [%d]에게 매칭
					  """.formatted(pgr.getRequester().getEmail(), pgr.getId(), creator.getUser().getEmail(),
					creator.getId());
			}
		);
		matchResultArray[size] = """
			대기중 요청 [%d]개, 작업 가능한 작업자 [%d]명 -> \n 총 [%d] 개의 요청이 매칭됨, 남은 요청 개수 : [%d]
			""".formatted(pendingRequestList.size(), availableCreatorList.size(), size,
			pendingRequestList.size() - size);
		discordService.sendToDiscord(String.join("\n", matchResultArray));
	}

	public boolean matchPictureGenerateRequest(PictureGenerateRequest pictureGenerateRequest) {
		List<Creator> availableCreatorList = creatorRepository.findAllAvailableCreator();
		if (availableCreatorList.isEmpty()) {
			String message = newRequestNotAvailable(pictureGenerateRequest);
			discordService.sendToDiscord(message);
			return false;
		}

		List<Long> alreadyMatchedCreatorIdList = matchingRegistry.getMatchedCreatorBefore(
			pictureGenerateRequest.getId());
		List<Creator> removeAlreadyMatchedCreatorList = availableCreatorList.stream()
			.filter(c -> !alreadyMatchedCreatorIdList.contains(c.getId()))
			.toList();
		if (removeAlreadyMatchedCreatorList.isEmpty()) {
			String message = notHaveNewCreatorMessage(pictureGenerateRequest);
			discordService.sendToDiscord(message);
			return false;
		}
		Creator randomSelectedCreator = RandomUtils.getRandomElement(removeAlreadyMatchedCreatorList);
		pictureGenerateRequest.assign(randomSelectedCreator);
		sendNotification(randomSelectedCreator);
		return true;
	}

	private static String newRequestNotAvailable(PictureGenerateRequest pictureGenerateRequest) {
		return """
			새로운 요청 id : [%d]에 대하여 작업 가능한 작업자가 없음""".formatted(pictureGenerateRequest.getId());
	}

	private static String notHaveNewCreatorMessage(PictureGenerateRequest pictureGenerateRequest) {
		return """
			매칭 취소되었던 요청 id : [%d]에 대하여 작업 가능한 작업자가 없음""".formatted(pictureGenerateRequest.getId());
	}

	private void sendNotification(Creator creator) {
		//TODO 공급자 앱에 푸시알림
		// edited at 2024-05-04
		// author
	}
}
