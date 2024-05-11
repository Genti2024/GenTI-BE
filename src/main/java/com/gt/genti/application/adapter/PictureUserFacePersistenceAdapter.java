package com.gt.genti.application.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gt.genti.application.port.in.PictureUserFacePort;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.repository.PictureUserFaceRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PictureUserFacePersistenceAdapter implements PictureUserFacePort {
	private final PictureUserFaceRepository pictureUserFaceRepository;
	@Override
	public List<PictureUserFace> findPictureByUrlIn(List<String> urls) {
		return pictureUserFaceRepository.findAllByUrlIsIn(urls);
	}

	@Override
	public List<PictureUserFace> saveAll(List<PictureUserFace> pictureUserFaceList) {
		return pictureUserFaceRepository.saveAll(pictureUserFaceList);
	}

}
