package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

	@Column(name = "deposit_amount")
	Long depositAmount;

	public void add(Long amount){
		if(amount < 0){
			throw new ExpectedException(DomainErrorCode.AddPointAmountCannotBeMinus);
		}
		this.depositAmount += amount;
	}

	public Deposit(User user) {
		this.user = user;
		this.depositAmount = 0L;
	}
}
