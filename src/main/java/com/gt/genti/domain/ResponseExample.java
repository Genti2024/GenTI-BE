package com.gt.genti.domain;

import com.gt.genti.domain.common.BaseTimeEntity;
import com.gt.genti.domain.common.Picture;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.CommonPictureUrlResponseDto;
import com.gt.genti.dto.ExampleSaveRequestDto;
import com.gt.genti.dto.PromptOnlyExampleSaveRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "response_example")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseExample extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "example_prompt")
	String examplePrompt;

	@Column(name = "prompt_only")
	Boolean promptOnly;

	@Column(name = "`key`")
	String key;

	@Setter
	@ManyToOne
	@JoinColumn(name = "uploaded_by")
	User uploadedBy;

	public ResponseExample(ExampleSaveRequestDto dto, User uploadedBy) {
		this.promptOnly = false;
		this.key = dto.getKey();
		this.examplePrompt = dto.getPrompt();
		this.setUploadedBy(uploadedBy);
	}

	public ResponseExample(PromptOnlyExampleSaveRequestDto dto, User uploadedBy) {
		this.promptOnly = true;
		this.key = null;
		this.examplePrompt = dto.getPrompt();
		this.setUploadedBy(uploadedBy);
	}

	public CommonPictureUrlResponseDto mapToCommonResponse() {
		return new CommonPictureUrlResponseDto(this.getId(), this.getKey());
	}

}
