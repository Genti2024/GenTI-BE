package com.gt.genti.application.port.in;

import java.util.Optional;

import com.gt.genti.domain.PicturePose;

public interface PicturePosePort {

	Optional<PicturePose> findByUrl(String url);
	PicturePose save(PicturePose picturePose);
}
