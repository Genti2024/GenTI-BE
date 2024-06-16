package com.gt.genti.domain;

import org.hibernate.annotations.ColumnDefault;

import com.gt.genti.domain.common.BaseEntity;
import com.gt.genti.domain.enums.WithdrawRequestStatus;
import com.gt.genti.domain.enums.converter.db.WithdrawRequestStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ColumnDefault("0")
	long amount;

	@Column(name = "task_count", nullable = false)
	@ColumnDefault("0")
	int taskCount;

	@Convert(converter = WithdrawRequestStatusConverter.class)
	@Column(name = "status", nullable = false)
	WithdrawRequestStatus status;

	public WithdrawRequest(Creator creator) {
		this.creator = creator;
		this.status = WithdrawRequestStatus.IN_PROGRESS;
	}

	public void update(long amount) {
		this.taskCount += 1;
		this.amount += amount;
	}

	public void complete(User modifiedBy) {
		this.setModifiedBy(modifiedBy);
		this.status = WithdrawRequestStatus.COMPLETED;
	}
}
