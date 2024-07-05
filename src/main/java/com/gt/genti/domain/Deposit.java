package com.gt.genti.domain;

import static com.gt.genti.error.ResponseCode.*;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.error.ExpectedException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "deposit")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	User user;

	@Column(name = "now_amount", nullable = false)
	Long nowAmount;

	@Column(name = "total_amount", nullable = false)
	Long totalAmount;

	@PrePersist
	public void prePersist() {
		if (this.nowAmount == null) {
			this.nowAmount = 0L;
		}
		if (this.totalAmount == null) {
			this.totalAmount = 0L;
		}
	}

	public void add(Long amount) {
		if (amount < 0) {
			throw ExpectedException.withLogging(AddPointAmountCannotBeMinus);
		}
		addInternal(amount);
	}

	private void addInternal(Long amount){
		this.nowAmount += amount;
		this.totalAmount += amount;
	}

	public void completeWithdraw(Long amount) {
		if (this.nowAmount < amount) {
			throw ExpectedException.withLogging(NotEnoughBalance);
		}
		this.nowAmount -= amount;
	}

	public Deposit(User user) {
		this.user = user;
	}
}
