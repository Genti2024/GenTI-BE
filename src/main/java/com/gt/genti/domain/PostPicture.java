package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "post_picture")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPicture extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	Post post;

	@OneToOne
	@JoinColumn(name = "picture_id", referencedColumnName = "id", nullable = false)
	Picture picture;

	@Builder
	public PostPicture(Long id, Picture picture) {
		this.id = id;
		this.picture = picture;
	}
}
