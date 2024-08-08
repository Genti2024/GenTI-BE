package com.gt.genti.picturegeneraterequest.service;

import static com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus.*;
import static com.gt.genti.picturegeneraterequest.service.PictureGenerateRequestCancellationReason.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PictureGenerateRequestExpireCheckScheduler {

	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;
	@Scheduled(fixedRate = 60 * 60 * 1000)
	@Transactional
	public void checkAndExpireRequests() {
		LocalDateTime timeoutThreshold = LocalDateTime.now().minusHours(12);
		List<PictureGenerateRequest> expiredRequests = pictureGenerateRequestRepository.findAllByCreatedAtBeforeAndPictureGenerateRequestStatusIn(
			timeoutThreshold,
			List.of(ASSIGNING, CREATED));
		if(!expiredRequests.isEmpty()){
			pictureGenerateRequestUseCase.cancelAllRequests(expiredRequests, NO_MATCHING);
			pictureGenerateRequestRepository.saveAll(expiredRequests);
		}

	}
}
