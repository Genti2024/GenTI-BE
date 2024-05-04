package com.gt.genti.application.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.adapter.in.web.PictureGenerateRequestPort;
import com.gt.genti.adapter.in.web.PictureGenerateRequestUseCase;
import com.gt.genti.adapter.in.web.PosePicturePort;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PosePicture;
import com.gt.genti.domain.User;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.dto.PictureGenerateRequestResponseDto;
import com.gt.genti.dto.PictureGenerateRequestSimplifiedResponseDto;
import com.gt.genti.other.util.RandomUtils;
import com.gt.genti.repository.CreatorRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateRequestService implements PictureGenerateRequestUseCase {
	private final PictureGenerateRequestPort pictureGenerateRequestPort;
	private final PosePicturePort posePicturePort;
	private final CreatorRepository creatorRepository;
	private final UserRepository userRepository;

	@Override
	public List<PictureGenerateRequestDetailResponseDto> getMyActivePictureGenerateRequest(Long userId) {
		return pictureGenerateRequestPort.findByRequestStatusIsActiveAndUserId_JPQL(userId).stream().map(
			PictureGenerateRequestDetailResponseDto::new
		).toList();
	}

	@Override
	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long id) {
		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findById(id).orElseThrow();
		return PictureGenerateRequestDetailResponseDto.builder()
			.pictureGenerateRequest(findPictureGenerateRequest)
			.build();
	}

	//TODO 내가 생성한 요청 리스트 보기
	// edited at 2024-04-13
	// author 서병렬
	@Override
	public List<PictureGenerateRequestSimplifiedResponseDto> getAllMyPictureGenerateRequests(User requester) {
		// return pictureGenerateRequestPersistenceAdapter.findAllByRequester(requester).stream().map(entity -> );
		return null;
	}

	@Override
	public PictureGenerateRequestResponseDto createPictureGenerateRequest(Long requesterId,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto) {

		User findRequester = userRepository.findById(requesterId).orElseThrow();
		String posePictureUrl = pictureGenerateRequestRequestDto.getPosePictureUrl();
		PosePicture findPosePicture = posePicturePort.findByUrl(
				posePictureUrl)
			.or(() -> Optional.of(posePicturePort.save(PosePicture.builder().url(
				posePictureUrl).build()))).orElseThrow();

		PictureGenerateRequest pgr = new PictureGenerateRequest(findRequester, pictureGenerateRequestRequestDto,
			findPosePicture);

		AtomicBoolean requestIsAssigned = new AtomicBoolean(false);
		List<Creator> creatorList = creatorRepository.findAllAvailableCreator();
		if (creatorList.isEmpty()) {
			requestIsAssigned.set(false);
		} else {
			Creator randomSelectedCreator = RandomUtils.getRandomElement(creatorList);
			pgr.assign(randomSelectedCreator);
			requestIsAssigned.set(true);
		}

		pictureGenerateRequestPort.save(
			new PictureGenerateRequest(findRequester, pictureGenerateRequestRequestDto, findPosePicture));

		if (requestIsAssigned.get()) {
			return PictureGenerateRequestResponseDto.builder().message("매칭되었당").build();
		} else {
			return PictureGenerateRequestResponseDto.builder().message("현재 매칭 가능한 공급자 없음").build();
		}

		//TODO 공급자 앱에 푸시알림
	}

	@Override
	@Transactional
	public Boolean modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto) {
		PictureGenerateRequest findPictureGenerateRequest = pictureGenerateRequestPort.findById(
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
