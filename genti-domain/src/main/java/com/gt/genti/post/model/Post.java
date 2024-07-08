package com.gt.genti.post.model;

import java.util.List;

import com.gt.genti.user.model.User;
import com.gt.genti.common.converter.PostStatusConverter;
import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.picture.post.model.PicturePost;

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

@Deprecated
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
