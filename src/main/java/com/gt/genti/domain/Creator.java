package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.enums.BankType;
import com.gt.genti.domain.enums.converter.BankTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
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

	@OneToOne()
	@JoinColumn(name = "user_id")
	User user;

	@OneToMany(mappedBy = "creator")
	List<PictureGenerateRequest> pictureGenerateRequest;

	@Convert(converter = BankTypeConverter.class)
	@Column(name = "bank_type")
	BankType bankType;


	@Column(name = "account_number")
	String accountNumber;

	@Builder
	public Creator(Boolean workable) {
		this.workable = workable;
	}

	public void updateAccountInfo(BankType bankType, String accountNumber){
		this.bankType = bankType;
		this.accountNumber = accountNumber;
	}
}
