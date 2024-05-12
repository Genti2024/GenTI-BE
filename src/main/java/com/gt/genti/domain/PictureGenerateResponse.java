package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.converter.PictureGenerateResponseStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "picture_generate_response")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureGenerateResponse extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	Creator creator;

	@OneToMany(mappedBy = "pictureGenerateResponse")
	List<PictureCompleted> completedPictureList;

	@OneToMany(mappedBy = "pictureGenerateResponse")
	List<PictureCreatedByCreator> createdByCreatorPictureList;

	@ManyToOne
	@JoinColumn(name = "request_id")
	PictureGenerateRequest request;

	@Column(name = "memo")
	String memo;

	@Convert(converter = PictureGenerateResponseStatusConverter.class)
	PictureGenerateResponseStatus status;

	public void updateStatus(PictureGenerateResponseStatus status) {
		this.status = status;
	}

	public void updateMemo(String memo) {
		this.memo = memo;
	}
}
