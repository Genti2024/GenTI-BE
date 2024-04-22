package com.gt.genti.scheduler;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.PictureGenerateRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledService {
	private final CreatorRepository creatorRepository;
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	@Transactional
	public void matchPictureGenerateRequests() {
		List<PictureGenerateRequest> pendingRequestList = pictureGenerateRequestRepository.findPendingRequests();
		if (pendingRequestList.isEmpty()) {
			log.info("매칭 대기중인 작업이 없음");
			return;
		}

		List<Creator> availableCreatorList = creatorRepository.findAllAvailableCreator();
		if (availableCreatorList.isEmpty()) {
			log.info("작업 가능한 작업자가 없음");
			return;
		}


		int size = Math.min(pendingRequestList.size(), availableCreatorList.size());
		IntStream.range(0, size).forEach(i -> {
				Creator creator = availableCreatorList.get(i);
				PictureGenerateRequest pgr = pendingRequestList.get(i);
				pgr.assign(creator);
			}
		);
	}
}
