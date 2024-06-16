package com.gt.genti.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.converter.db.PictureGenerateResponseStatusConverter;

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

	@Convert(converter = PictureGenerateResponseStatusConverter.class)
	PictureGenerateResponseStatus status;

	@Column(name = "memo", nullable = false)
	@ColumnDefault("")
	String memo;

	@Column(name = "admin_in_charge", nullable = false)
	@ColumnDefault("")
	String adminInCharge;

	@Column(name = "submitted_by_creator_at")
	LocalDateTime submittedByCreatorAt;
	@Column(name = "submitted_by_admin_at")
	LocalDateTime submittedByAdminAt;

	public Duration creatorSubmitAndGetElaspedTime() {
		//TODO 공급자가 제출시 불가한 상태가 있는지 생각해볼것
		// edited at 2024-05-20
		// author 서병렬
		this.submittedByCreatorAt = LocalDateTime.now();
		this.status = PictureGenerateResponseStatus.SUBMITTED_FIRST;
		return getCreatorElapsedTime();
	}

	private Duration getCreatorElapsedTime() {
		return Duration.between(this.getCreatedAt(), this.getSubmittedByCreatorAt());
	}

	public void updateMemo(String memo) {
		this.memo = memo;
	}

	public void updateInChargeAdmin(String adminInCharge) {
		this.adminInCharge = adminInCharge;
		this.status = PictureGenerateResponseStatus.ADMIN_IN_PROGRESS;
	}

	public void adminSubmit() {
		this.submittedByAdminAt = LocalDateTime.now();
		this.status = PictureGenerateResponseStatus.SUBMITTED_FINAL;
		this.request.completed();
	}

	public PictureGenerateResponse(Creator creator, PictureGenerateRequest request) {
		this.creator = creator;
		this.request = request;
		this.status = PictureGenerateResponseStatus.BEFORE_WORK;
	}

	public Duration getAdminElapsedTime() {
		return Duration.between(this.getCreatedAt(), this.getSubmittedByAdminAt());
	}
}
