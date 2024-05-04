package com.gt.genti.application.port.in;

import java.util.Optional;

import com.gt.genti.domain.PosePicture;

public interface PosePicturePort {

	Optional<PosePicture> findByUrl(String url);
	PosePicture save(PosePicture posePicture);
}
