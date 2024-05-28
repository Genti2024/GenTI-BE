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

@Table(name = "picture_user_face")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureUserFace extends PictureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "url", nullable = false)
	String url;

	@Builder
	public PictureUserFace(String url, User user) {
		this.url = url;
		this.setUploadedBy(user);
	}

	public void modify(String url) {
		this.url = url;
	}
}
