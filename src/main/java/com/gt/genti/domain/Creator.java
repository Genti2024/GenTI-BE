package com.gt.genti.domain;

import java.util.List;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	Boolean workable;

	@OneToOne(mappedBy = "creator")
	User user;

	@OneToMany(mappedBy = "creator")
	List<PictureCreateRequest> pictureCreateRequest;

	@Builder
	public Creator(Boolean workable) {
		this.workable = workable;
	}
}
