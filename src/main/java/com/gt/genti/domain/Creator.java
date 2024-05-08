package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
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

@Table(name = "creator")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "workable", nullable = false)
	Boolean workable;

	@OneToOne()
	@JoinColumn(name = "user_id")
	User user;

	@OneToMany(mappedBy = "creator")
	List<PictureGenerateRequest> pictureGenerateRequest;

	@Builder
	public Creator(Boolean workable) {
		this.workable = workable;
	}
}
