package com.gt.genti.application.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gt.genti.application.port.in.PicturePosePort;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.repository.PicturePoseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PicturePosePersistenceAdapter implements PicturePosePort {
	private final PicturePoseRepository picturePoseRepository;

	@Override
	public Optional<PicturePose> findByKey(String key) {
		return picturePoseRepository.findByKey(key);
	}

	@Override
	public PicturePose save(PicturePose picturePose) {
		return picturePoseRepository.save(picturePose);
	}
}
