package com.gt.genti.domain.common;

import com.gt.genti.domain.User;
import com.gt.genti.dto.common.CommonPictureUrlResponseDto;

import jakarta.persistence.Column;
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

	@Override
	public CommonPictureUrlResponseDto mapToCommonResponse() {
		return new CommonPictureUrlResponseDto(this.getId(), this.getKey());
	}

}
