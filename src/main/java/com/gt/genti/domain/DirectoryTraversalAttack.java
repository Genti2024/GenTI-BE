package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectoryTraversalAttack extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "url")
	String url;

	@Column(name = "count")
	Long count;

	public DirectoryTraversalAttack(String url) {
		this.url = url;
		this.count = 1L;
	}

	public void addCount(){
		this.count +=1;
	}

	@Builder
	public DirectoryTraversalAttack(Long id, String url, Long count) {
		this.id = id;
		this.url = url;
		this.count = count;
	}
}