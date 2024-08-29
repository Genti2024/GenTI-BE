package com.gt.genti.withdrawrequest.model;

import com.gt.genti.common.baseentity.model.BaseEntity;
import com.gt.genti.common.converter.WithdrawRequestStatusConverter;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "withdraw_request")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawRequest extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
	Creator creator;

	@Column(name = "amount", nullable = false)
	Long amount;

	@Column(name = "task_count", nullable = false)
	Integer taskCount;

	@Convert(converter = WithdrawRequestStatusConverter.class)
	@Column(name = "status", nullable = false)
	CashoutStatus status;

	@PrePersist
	public void prePersist() {
		if (this.amount == null) {
			this.amount = 0L;
		}
		if (this.taskCount == null) {
			this.taskCount = 0;
		}
		if (this.status == null) {
			this.status = CashoutStatus.IN_PROGRESS;
		}
	}

	public WithdrawRequest(Creator creator) {
		this.creator = creator;
		this.status = CashoutStatus.IN_PROGRESS;
	}

	public void addSettlement(long amount) {
		this.taskCount += 1;
		this.amount += amount;
	}

	public void complete(User modifiedBy) {
		this.setModifiedBy(modifiedBy);
		this.status = CashoutStatus.COMPLETED;
	}
}
