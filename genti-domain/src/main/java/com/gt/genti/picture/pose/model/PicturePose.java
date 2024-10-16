package com.gt.genti.picture.pose.model;

import com.gt.genti.user.model.User;
import com.gt.genti.common.picture.model.PictureEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "picture_pose")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PicturePose extends PictureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	public PicturePose(String key, User uploadedBy) {
		this.key = key;
		this.setUploadedBy(uploadedBy);
	}

	public void modify(String key) {
		this.key = key;
	}

}
