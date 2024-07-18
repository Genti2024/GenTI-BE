package com.gt.genti.picturegenerateresponse.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.common.converter.PictureGenerateResponseStatusConverter;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
	String memo;

	@Column(name = "admin_in_charge", nullable = false)
	String adminInCharge;

	@Column(name = "submitted_by_creator_at")
	LocalDateTime submittedByCreatorAt;
	@Column(name = "submitted_by_admin_at")
	LocalDateTime submittedByAdminAt;

	@Column(name = "star")
	Integer star;

	@PrePersist
	public void prePersist() {
		if (this.memo == null) {
			this.memo = "";
		}
		if (this.adminInCharge == null) {
			this.adminInCharge = "";
		}
	}

	public Duration creatorSubmitAndGetElapsedTime() {
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
		this.request.submittedByAdmin();
	}

	public void userVerified(){
		this.status = PictureGenerateResponseStatus.COMPLETED;
	}

	public PictureGenerateResponse(Creator creator, PictureGenerateRequest request) {
		this.creator = creator;
		this.request = request;
		this.status = PictureGenerateResponseStatus.ADMIN_BEFORE_WORK;
	}

	public Duration getAdminElapsedTime() {
		return Duration.between(this.getCreatedAt(), this.getSubmittedByAdminAt());
	}

	public void updateStar(Integer star) {
		this.star = star;
	}
}
