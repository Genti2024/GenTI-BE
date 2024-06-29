package com.gt.genti.picture.port;

import java.util.List;

import com.gt.genti.picture.userface.model.PictureUserFace;

public interface PictureUserFacePort {
	List<PictureUserFace> findPictureByUrlIn(List<String> urls);
	List<PictureUserFace> saveAll(List<PictureUserFace> pictureUserFaceList);
}
