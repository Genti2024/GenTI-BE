package com.gt.genti.application.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gt.genti.application.port.in.PosePicturePort;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.repository.PosePictureRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PosePicturePersistenceAdapter implements PosePicturePort {
	private final PosePictureRepository posePictureRepository;

	@Override
	public Optional<PicturePose> findByUrl(String url) {
		return posePictureRepository.findByUrl(url);
	}

	@Override
	public PicturePose save(PicturePose picturePose) {
		return posePictureRepository.save(picturePose);
	}
}
