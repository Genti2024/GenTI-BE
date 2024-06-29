package com.gt.genti.responseexample.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.picture.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.picture.responseexample.model.ResponseExample;
import com.gt.genti.picture.responseexample.repository.ResponseExampleRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.responseexample.command.ExampleSaveCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseExampleService {
	private final ResponseExampleRepository responseExampleRepository;
	private final UserRepository userRepository;

	public Page<ExampleWithPictureFindResponseDto> getAllResponseExamplesPagination(Pageable pageable) {
		return responseExampleRepository.findAllByPromptOnlyIsFalse(pageable)
			.map(ExampleWithPictureFindResponseDto::new);
	}

	public List<ExampleWithPictureFindResponseDto> getAllResponseExamples() {
		return responseExampleRepository.findAllByPromptOnlyIsFalse()
			.stream()
			.map(ExampleWithPictureFindResponseDto::new)
			.toList();
	}

	@Transactional
	public void addResponseExamples(List<ExampleSaveCommand> commandList,
		Long userId) {
		User foundUploader = userRepository.findById(userId).orElseThrow(() -> ExpectedException.withLogging(
			ResponseCode.UserNotFound, userId));
		responseExampleRepository.saveAll(
			commandList.stream().map(command -> ResponseExample.builder()
				.key(command.getKey())
				.uploadedBy(foundUploader)
				.pictureRatio(command.getPictureRatio())
				.prompt(command.getPrompt())
				.build()).toList());
	}

}
