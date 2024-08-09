package com.gt.genti.responseexample.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.responseexample.model.ResponseExample;
import com.gt.genti.picture.responseexample.repository.ResponseExampleRepository;
import com.gt.genti.responseexample.command.ExampleSaveCommand;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponseExampleService {
	private final ResponseExampleRepository responseExampleRepository;
	private final UserRepository userRepository;

	public Page<ExampleWithPictureFindResponseDto> getAllResponseExamplesPagination(Pageable pageable) {
		return responseExampleRepository.findAllByPromptOnlyIsFalse(pageable)
			.map(ExampleWithPictureFindResponseDto::new);
	}

	public List<ExampleWithPictureFindResponseDto> getAllResponseExamples() {
		List<ExampleWithPictureFindResponseDto> examples = responseExampleRepository.findAllByPromptOnlyIsFalse()
				.stream()
				.map(ExampleWithPictureFindResponseDto::new)
				.collect(Collectors.toList());

		Collections.shuffle(examples);

		Optional<ExampleWithPictureFindResponseDto> firstMatching_Ratio_GARO = examples.stream()
				.filter(example -> PictureRatio.RATIO_GARO.equals(example.getPicture().getPictureRatio()))
				.findFirst();

		firstMatching_Ratio_GARO.ifPresent(matchingExample -> {
			examples.remove(matchingExample);
			examples.add(0, matchingExample);
		});

		return examples;
	}

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
