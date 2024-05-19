package com.gt.genti.domain.common;

import java.util.List;

import com.gt.genti.domain.Picture;
import com.gt.genti.domain.User;
import com.gt.genti.dto.CommonPictureResponseDto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class PictureEntity extends BaseTimeEntity implements Picture {

	@ManyToOne
	@JoinColumn(name = "uploaded_by")
	User uploadedBy;

	@Override
	public CommonPictureResponseDto mapToCommonResponse() {
		return new CommonPictureResponseDto(this.getId(), this.getUrl());
	}

}
