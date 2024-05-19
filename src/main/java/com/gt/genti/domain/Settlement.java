package com.gt.genti.domain;

import java.time.Duration;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	Boolean withdrawn;

	@Builder

	public Settlement(PictureGenerateResponse pictureGenerateResponse, Duration elapsed, Long reward) {
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.elapsedMinutes = elapsed.toMinutes();
		this.reward = reward;
	}
}
