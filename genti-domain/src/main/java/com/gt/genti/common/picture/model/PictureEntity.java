package com.gt.genti.common.picture.model;

import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.common.picture.Picture;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.common.converter.PictureRatioConverter;
import com.gt.genti.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class PictureEntity extends BaseTimeEntity implements Picture {

	@ManyToOne
	@JoinColumn(name = "uploaded_by")
	protected User uploadedBy;

	@Column(name = "`key`", nullable = false)
	protected String key;

	@Column(name = "picture_ratio")
	@Convert(converter = PictureRatioConverter.class)
	protected PictureRatio pictureRatio;

}
