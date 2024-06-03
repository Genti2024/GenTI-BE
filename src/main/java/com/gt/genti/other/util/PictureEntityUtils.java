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

import lombok.Builder;

public class PictureEntityUtils {
	@Builder
	public static PictureCompleted makePictureCompleted(String url, PictureGenerateResponse pgres, User uploadedBy) {
		return new PictureCompleted(url, pgres, uploadedBy);
	}

	@Builder
	public static PictureCreatedByCreator makePictureCreatedByCreator(String url, PictureGenerateResponse pgres,
		User uploadedBy) {

		return new PictureCreatedByCreator(url, pgres, uploadedBy);
	}

	@Builder
	public static PictureProfile makePictureProfile(String url, User uploadedBy) {
		return new PictureProfile(url, uploadedBy);
	}

	@Deprecated
	@Builder
	public static PicturePost makePicturePost(String url, Post post, User uploadedBy) {
		return new PicturePost(url, post, uploadedBy);
	}

	@Builder
	public static PicturePose makePicturePose(String url, User uploadedBy) {

		return new PicturePose(url, uploadedBy);
	}

	@Builder
	public static PictureUserFace makePictureUserFace(String url, User uploadedBy) {
		return new PictureUserFace(url, uploadedBy);
	}
}
