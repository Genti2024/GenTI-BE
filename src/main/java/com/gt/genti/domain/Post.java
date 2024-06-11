package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.enums.PostStatus;
import com.gt.genti.domain.enums.converter.db.PostStatusConverter;
import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "post")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

	@OneToMany(mappedBy = "post")
	List<PicturePost> pictureList;

	@OneToOne
	@JoinColumn(name = "main_picture_id")
	PicturePost mainPicture;

	String content;

	Integer likes;

	@Convert(converter = PostStatusConverter.class)
	PostStatus postStatus;

}
