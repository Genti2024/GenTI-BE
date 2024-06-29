package com.gt.genti.settlement.model;

import com.gt.genti.withdrawrequest.model.WithdrawRequest;
import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "settlement")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne
	@JoinColumn(name = "picture_generate_response_id", referencedColumnName = "id", nullable = false)
	PictureGenerateResponse pictureGenerateResponse;

	@Column(name = "elapsed_minutes")
	Long elapsedMinutes;

	@Column(name = "reward")
	Long reward;

	@Setter
	@ManyToOne
	@JoinColumn(name = "withdraw_request_id")
	WithdrawRequest withdrawRequest;

	@Builder
	public Settlement(PictureGenerateResponse pictureGenerateResponse, Long elapsedMinutes, Long reward) {
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.elapsedMinutes = elapsedMinutes;
		this.reward = reward;
	}

}
