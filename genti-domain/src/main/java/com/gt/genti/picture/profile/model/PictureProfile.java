package com.gt.genti.picture.profile.model;

import com.gt.genti.user.model.User;
import com.gt.genti.common.picture.model.PictureEntity;

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
@Deprecated
@Table(name = "picture_profile")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureProfile extends PictureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

	public PictureProfile(String key, User user) {
		this.key = key;
		this.user = user;
	}

	public void modify(String key) {
		this.key = key;
	}
}
