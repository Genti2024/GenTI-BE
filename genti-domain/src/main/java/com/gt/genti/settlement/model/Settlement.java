package com.gt.genti.settlement.model;

import com.gt.genti.cashout.model.Cashout;
import com.gt.genti.common.basetimeentity.model.BaseTimeEntity;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;

import jakarta.persistence.*;
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

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "picture_generate_response_id", referencedColumnName = "id", nullable = false)
	PictureGenerateResponse pictureGenerateResponse;

	@Column(name = "elapsed_minutes")
	Long elapsedMinutes;

	@Column(name = "reward")
	Long reward;

	@Setter
	@ManyToOne
	@JoinColumn(name = "cashout_id")
	Cashout cashout;

	@Builder
	public Settlement(PictureGenerateResponse pictureGenerateResponse, Long elapsedMinutes, Long reward) {
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.elapsedMinutes = elapsedMinutes;
		this.reward = reward;
	}

}
