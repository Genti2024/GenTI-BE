package com.gt.genti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.ResponseExample;
import com.gt.genti.domain.User;
import com.gt.genti.dto.AddPromptOnlyExampleRequestDto;
import com.gt.genti.dto.AddResponseExampleRequestDto;
import com.gt.genti.dto.PromptOnlyExampleResponseDto;
import com.gt.genti.dto.ResponseExampleResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.ResponseExampleRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseExampleService {
	private final UserRepository userRepository;
	private final ResponseExampleRepository responseExampleRepository;

	public List<ResponseExampleResponseDto> getAllResponseExamples() {
		return responseExampleRepository.findAllByPromptOnlyIsFalse()
			.stream()
			.map(ResponseExampleResponseDto::new)
			.toList();
	}

	public List<PromptOnlyExampleResponseDto> getAllPromptOnlyExamples() {
		return responseExampleRepository.findAllByPromptOnlyIsFalse()
			.stream()
			.map(PromptOnlyExampleResponseDto::new)
			.toList();
	}

	@Transactional
	public void addResponseExamples(List<AddResponseExampleRequestDto> requestDtoList,
		Long userId) {
		User findAdmin = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));


		responseExampleRepository.saveAll(
			requestDtoList.stream().map(dto -> new ResponseExample(dto, findAdmin)).toList());
	}

	@Transactional
	public void addPromptOnlyExamples(List<AddPromptOnlyExampleRequestDto> requestDtoList, Long userId) {
		User findAdmin = userRepository.findById(userId)
			.orElseThrow(() -> new ExpectedException(ErrorCode.UserNotFound));
		responseExampleRepository.saveAll(
			requestDtoList.stream().map(dto -> new ResponseExample(dto, findAdmin)).toList());
	}
}
