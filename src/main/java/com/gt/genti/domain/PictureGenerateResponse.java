package com.gt.genti.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.converter.PictureGenerateResponseStatusConverter;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;

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

	public void creatorSubmit() {
		//TODO 공급자가 제출시 불가한 상태가 있는지 생각해볼것
		// edited at 2024-05-20
		// author 서병렬
		this.status = PictureGenerateResponseStatus.SUBMITTED_FIRST;
	}

	public void updateMemo(String memo) {
		this.memo = memo;
	}

	public Duration getElapsedTime() {
		return Duration.between(this.getCreatedAt(), LocalDateTime.now());
	}

	public void adminSubmit() {
		this.status = PictureGenerateResponseStatus.SUBMITTED_FINAL;
	}
}
