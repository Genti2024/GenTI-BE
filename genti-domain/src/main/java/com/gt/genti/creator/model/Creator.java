package com.gt.genti.creator.model;

import java.util.ArrayList;
import java.util.List;

import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.common.converter.BankTypeConverter;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "creator")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Setter
	@Column(name = "workable", nullable = false)
	Boolean workable;

	// creator 개별적으로 삭제가능
	@OneToOne
	@JoinColumn(name = "user_id")
	User user;

	@OneToMany(mappedBy = "creator")
	List<PictureGenerateRequest> pictureGenerateRequestList = new ArrayList<>();

	@OneToMany(mappedBy = "creator")
	List<PictureGenerateResponse> pictureGenerateResponseList = new ArrayList<>();

	@Convert(converter = BankTypeConverter.class)
	@Column(name = "bank_type", nullable = false)
	BankType bankType;

	@Column(name = "account_number", nullable = false)
	String accountNumber;

	@Column(name = "account_holder", nullable = false)
	String accountHolder;

	@Column(name = "completed_task_count", nullable = false)
	int completedTaskCount;

	@PrePersist
	public void prePersist() {
		if (this.bankType == null) {
			this.bankType = BankType.NONE;
		}
		if (this.workable == null) {
			this.workable = true;
		}
		if (this.accountNumber == null) {
			this.accountNumber = "";
		}
		if (this.accountHolder == null) {
			this.accountHolder = "";
		}
	}

	public Creator(User user) {
		this.workable = true;
		this.user = user;
		this.bankType = BankType.NONE;
		this.completedTaskCount = 0;
	}

	public void updateAccountInfo(BankType bankType, String accountNumber, String accountHolder) {
		this.bankType = bankType;
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
	}

	public void completeTask() {
		this.completedTaskCount += 1;
	}

	public void addPictureGenerateRequest(PictureGenerateRequest request) {
		this.pictureGenerateRequestList.add(request);
	}

	public void addPictureGenerateResponse(PictureGenerateResponse response) {
		this.pictureGenerateResponseList.add(response);
	}
}
