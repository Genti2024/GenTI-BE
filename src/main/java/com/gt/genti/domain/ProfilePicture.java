package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class ProfilePicture extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne(mappedBy = "profilePicture")
	User user;

	@OneToOne
	@JoinColumn(name = "picture_id", referencedColumnName = "id")
	Picture picture;

	@Builder
	public ProfilePicture(Long id, Picture picture) {
		this.id = id;
		this.picture = picture;
	}
}
