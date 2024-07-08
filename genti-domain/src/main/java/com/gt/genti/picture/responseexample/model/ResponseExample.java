package com.gt.genti.picture.responseexample.model;

import com.gt.genti.common.picture.Picture;
import com.gt.genti.common.picture.model.PictureEntity;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "response_example")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseExample extends PictureEntity implements Picture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "example_prompt")
	String examplePrompt;

	@Column(name = "prompt_only")
	Boolean promptOnly;

	@Builder
	public ResponseExample(String key, String prompt, PictureRatio pictureRatio, User uploadedBy) {
		this.promptOnly = false;
		this.key = key;
		this.examplePrompt = prompt;
		this.setUploadedBy(uploadedBy);
		this.pictureRatio = pictureRatio;
	}
}
