package com.gt.genti.picture.completed.model;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;
import com.gt.genti.common.picture.model.PictureEntity;
import com.gt.genti.picture.PictureRatio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "picture_completed")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureCompleted extends PictureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "requester_id")
	User requester;

	@ManyToOne
	@JoinColumn(name = "picture_generate_response_id")
	PictureGenerateResponse pictureGenerateResponse;

	@Builder
	public PictureCompleted(String key, PictureGenerateResponse pictureGenerateResponse, User uploadedBy, User requester, PictureRatio pictureRatio) {
		this.key = key;
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.setUploadedBy(uploadedBy);
		this.requester =requester;
		this.pictureRatio =pictureRatio;
	}

	public void modify(String key) {
		this.key = key;
	}
}
