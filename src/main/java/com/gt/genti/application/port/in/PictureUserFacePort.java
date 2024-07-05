package com.gt.genti.application.port.in;

import java.util.List;

import com.gt.genti.domain.PictureUserFace;

public interface PictureUserFacePort {
	List<PictureUserFace> findPictureByUrlIn(List<String> urls);
	List<PictureUserFace> saveAll(List<PictureUserFace> pictureUserFaceList);
}
