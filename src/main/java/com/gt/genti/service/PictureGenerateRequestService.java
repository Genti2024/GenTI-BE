package com.gt.genti.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PosePicture;
import com.gt.genti.domain.User;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.repository.PictureGenerateRequestRepository;
import com.gt.genti.repository.PosePictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateRequestService {
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;
	private final PosePictureRepository posePictureRepository;

	public List<PictureGenerateRequestDetailResponseDto> getMyActivePictureGenerateRequest(Long userId) {
		return pictureGenerateRequestRepository.findByRequestStatusIsActiveAndUserId_JPQL(userId).stream().map(
			PictureGenerateRequestDetailResponseDto::new
		).toList();
	}

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long id) {
		PictureGenerateRequest findPictureGenerateRequest =pictureGenerateRequestRepository.findById(id).orElseThrow();
		return PictureGenerateRequestDetailResponseDto.builder()
			.pictureGenerateRequest(findPictureGenerateRequest)
			.build();
	}

	//TODO 내가 생성한 요청 리스트 보기
	// edited at 2024-04-13
	// author 서병렬

	// public List<PictureGenerateRequestSimplifiedResponseDto> getAllMyPictureGenerateRequests(User requester){
	// 	return pictureGenerateRequestRepository.findAllByRequester(requester).stream().map(entity -> );
	// }

	public Boolean createPictureGenerateRequest(User requester,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto) {
		String posePictureUrl = pictureGenerateRequestRequestDto.getPosePictureUrl();
		PosePicture findPosePicture = posePictureRepository.findByUrl(
				posePictureUrl)
			.or(() -> Optional.of(posePictureRepository.save(PosePicture.builder().url(
				posePictureUrl).build()))).orElseThrow();

		pictureGenerateRequestRepository.save(
			new PictureGenerateRequest(requester, pictureGenerateRequestRequestDto, findPosePicture));

		//TODO 공급자 선택 로직 + 공급자 앱에 푸시알림
		// edited at 2024-04-13
		// author 서병렬
		return true;
	}

	@Transactional
	public Boolean modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto) {
		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestRepository.findById(
			pictureGenerateRequestModifyDto.getPictureGenerateRequestId()).orElseThrow();

		if (!Objects.equals(userId, findPictureGenerateRequest.getRequester().getId())) {
			throw new RuntimeException("자신이 작성한 요청만 수정 가능");
		}

		if (findPictureGenerateRequest.getCreator() != null) {
			throw new RuntimeException("이미 작업이 진행중인 요청은 수정이 불가합니다.");
		}

		PosePicture posePicture = findPictureGenerateRequest.getPosePicture();
		String modifyPosePictureUrl = findPictureGenerateRequest.getPosePicture().getUrl();

		if (!Objects.equals(posePicture.getUrl(),
			pictureGenerateRequestModifyDto.getPosePictureUrl())) {
			posePicture.modify(modifyPosePictureUrl);
		}

		findPictureGenerateRequest.modify(pictureGenerateRequestModifyDto, posePicture);
		return true;
	}

}