package com.gt.genti.picture.userface.model;

import com.gt.genti.user.model.User;
import com.gt.genti.common.picture.model.PictureEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Builder
	public PictureUserFace(String key, User user) {
		this.key = key;
		this.setUploadedBy(user);
	}

	public void modify(String key) {
		this.key = key;
	}
}
