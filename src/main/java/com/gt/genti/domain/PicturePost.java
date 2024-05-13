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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "picture_post")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PicturePost extends PictureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	Post post;

	@Column(name = "url", nullable = false)
	String url;

	public PicturePost(String url, Post post) {
		this.url = url;
		this.post = post;
	}
}
