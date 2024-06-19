package com.gt.genti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.admin.ExampleSaveCommand;
import com.gt.genti.domain.ResponseExample;
import com.gt.genti.domain.User;
import com.gt.genti.dto.admin.request.ExampleSaveRequestDto;
import com.gt.genti.dto.admin.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.repository.ResponseExampleRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseExampleService {
	private final ResponseExampleRepository responseExampleRepository;

	public List<ExampleWithPictureFindResponseDto> getAllResponseExamples() {
		return responseExampleRepository.findAllByPromptOnlyIsFalse()
			.stream()
			.map(ExampleWithPictureFindResponseDto::new)
			.toList();
	}

	@Transactional
	public void addResponseExamples(List<ExampleSaveCommand> commandList,
		User user) {
		responseExampleRepository.saveAll(
			commandList.stream().map(command -> new ResponseExample(command, user)).toList());
	}

}
