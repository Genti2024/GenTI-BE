package com.gt.genti.picture.port;

import java.util.Optional;

import com.gt.genti.picture.pose.model.PicturePose;

public interface PicturePosePort {

	Optional<PicturePose> findByKey(String key);
	PicturePose save(PicturePose picturePose);
}
