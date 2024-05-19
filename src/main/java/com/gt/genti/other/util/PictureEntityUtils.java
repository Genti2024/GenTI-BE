package com.gt.genti.other.util;

import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.PicturePose;
import com.gt.genti.domain.PicturePost;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.Post;
import com.gt.genti.domain.User;

public class PictureEntityUtils {
	public static PictureCompleted makePictureCompleted(String url, PictureGenerateResponse pgres, User uploadedBy) {
		return new PictureCompleted(url, pgres, uploadedBy);
	}

	public static PictureCreatedByCreator makePictureCreatedByCreator(String url, PictureGenerateResponse pgres,
		User uploadedBy) {

		return new PictureCreatedByCreator(url, pgres, uploadedBy);
	}

	public static PictureProfile makePictureProfile(String url, User user) {
		return new PictureProfile(url, user);
	}

	public static PicturePost makePicturePost(String url, Post post) {
		return new PicturePost(url, post);
	}

	public static PicturePose makePicturePose(String url, User uploadedBy) {

		return new PicturePose(url, uploadedBy);
	}

	public static PictureUserFace makePictureUserFace(String url, User user) {
		return new PictureUserFace(url, user);
	}
}
