package com.gt.genti.picturegenerateresponse.service;

import static com.gt.genti.picturegeneraterequest.service.PictureGenerateRequestCancellationReason.*;
import static com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateResponseExpireCheckScheduler {
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final PictureGenerateWorkService pictureGenerateWorkService;
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Scheduled(fixedRate = 60 * 60 * 1000)
	@Transactional
	public void checkAndExpireRequests() {
		LocalDateTime timeoutThreshold = LocalDateTime.now().minusHours(6);
		List<PictureGenerateResponse> expiredResponseList = pictureGenerateResponseRepository.findAllByCreatedAtBeforeAndStatusIs(
			timeoutThreshold,
			CREATOR_BEFORE_WORK);
		if (!expiredResponseList.isEmpty()) {
			expiredResponseList.forEach(response -> {
				pictureGenerateWorkService.expire(response);
				pictureGenerateRequestUseCase.cancelRequest(response.getRequest(),
					SUPPLIER_DID_NOT_WORK);
			});

		}
		pictureGenerateResponseRepository.saveAll(expiredResponseList);
	}
}
