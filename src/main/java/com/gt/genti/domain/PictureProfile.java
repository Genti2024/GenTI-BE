package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profile_picture")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureProfile extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne(mappedBy = "pictureProfile")
	User user;

	@Column(name = "url", nullable = false)
	String url;

	@Builder
	public PictureProfile(Long id, String url) {
		this.id = id;
		this.url = url;
	}

	public void modify(String url){
		this.url = url;
	}
}
