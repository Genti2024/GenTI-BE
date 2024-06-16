package com.gt.genti.domain;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.BankType;
import com.gt.genti.domain.enums.Sex;
import com.gt.genti.domain.enums.UserStatus;
import com.gt.genti.domain.enums.converter.db.BankTypeConverter;

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

	//TODO Creator 삭제시 고아 request re-match 로직 개발
	// edited at 2024-05-27
	// author 서병렬
	@OneToMany(mappedBy = "creator")
	List<PictureGenerateRequest> pictureGenerateRequest;

	@Convert(converter = BankTypeConverter.class)
	@Column(name = "bank_type", nullable = false)
	BankType bankType;

	@Column(name = "account_number", nullable = false)
	@ColumnDefault("")
	String accountNumber;

	@Column(name = "account_holder", nullable = false)
	@ColumnDefault("")
	String accountHolder;

	@Column(name = "completed_task_count", nullable = false)
	int completedTaskCount;

	@PrePersist
	public void prePersist() {
		if (this.bankType == null) {
			this.bankType = BankType.NONE;
		}
		if(this.workable == null){
			this.workable = true;
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
}
