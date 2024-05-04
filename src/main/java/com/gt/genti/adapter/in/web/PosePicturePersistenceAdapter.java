package com.gt.genti.adapter.in.web;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gt.genti.domain.PosePicture;
import com.gt.genti.repository.PosePictureRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PosePicturePersistenceAdapter implements PosePicturePort {
	private final PosePictureRepository posePictureRepository;

	@Override
	public Optional<PosePicture> findByUrl(String url) {
		return posePictureRepository.findByUrl(url);
	}

	@Override
	public PosePicture save(PosePicture posePicture) {
		return posePictureRepository.save(posePicture);
	}
}
