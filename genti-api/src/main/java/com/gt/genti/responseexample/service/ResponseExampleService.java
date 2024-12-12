package com.gt.genti.responseexample.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gt.genti.responseexample.dto.response.ExampleWithSquarePicture;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.responseexample.repository.ResponseExampleRepository;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponseExampleService {
	private final ResponseExampleRepository responseExampleRepository;

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

	public List<ExampleWithSquarePicture> getAllResponseExamplesInGenerateView() {
		final String type = "FREE_ONE";
		List<ExampleWithSquarePicture> examples = responseExampleRepository.findAllByType(type)
				.stream()
				.map(ExampleWithSquarePicture::new)
				.collect(Collectors.toList());
		Collections.shuffle(examples);

		return examples.subList(0, 5);
	}

	public List<ExampleWithSquarePicture> getAllResponseExamplesInGenerateViewV2(String type) {
		List<ExampleWithSquarePicture> examples = responseExampleRepository.findAllByType(type)
				.stream()
				.map(ExampleWithSquarePicture::new)
				.collect(Collectors.toList());
		Collections.shuffle(examples);

		return examples.subList(0, 5);
	}
}
