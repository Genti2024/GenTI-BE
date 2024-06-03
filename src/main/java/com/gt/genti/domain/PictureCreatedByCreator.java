package com.gt.genti.domain;

import com.gt.genti.domain.common.PictureEntity;

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

@Table(name = "picture_created_by_creator")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureCreatedByCreator extends PictureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "picture_generate_response_id")
	PictureGenerateResponse pictureGenerateResponse;

	@Builder
	public PictureCreatedByCreator(String key, PictureGenerateResponse pictureGenerateResponse, User uploadedBy) {
		this.key = key;
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.uploadedBy = uploadedBy;
	}

	public void modify(String key) {
		this.key = key;
	}
}
