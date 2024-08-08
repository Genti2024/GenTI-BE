package com.gt.genti.util;

import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator;
import com.gt.genti.picture.pose.model.PicturePose;
import com.gt.genti.picture.post.model.PicturePost;
import com.gt.genti.picture.profile.model.PictureProfile;
import com.gt.genti.picture.userface.model.PictureUserFace;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.post.model.Post;
import com.gt.genti.user.model.User;

import lombok.Builder;

public class PictureEntityUtils {

	@Builder
	public static PictureCompleted makePictureCompleted(String url, PictureGenerateResponse pgres, User uploadedBy,
		User requester,
		PictureRatio pictureRatio) {
		return new PictureCompleted(url, pgres, uploadedBy, requester, pictureRatio);
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
