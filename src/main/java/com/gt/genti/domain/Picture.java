package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "picture")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Setter
	String url;

	@Builder
	public Picture(Long id, String url) {
		this.id = id;
		this.url = url;
	}
}
