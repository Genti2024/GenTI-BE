package com.gt.genti.domain;

import com.gt.genti.domain.common.PictureEntity;

import jakarta.persistence.Column;
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

	@Column(name = "url", nullable = false)
	String url;

	@ManyToOne
	@JoinColumn(name = "picture_generate_response_id")
	PictureGenerateResponse pictureGenerateResponse;

	@Builder
	public PictureCreatedByCreator(String url, PictureGenerateResponse pictureGenerateResponse, Long uploadedBy) {

		this.url = url;
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.setUploadedBy(uploadedBy);
	}

	public void modify(String url) {
		this.url = url;
	}
}
