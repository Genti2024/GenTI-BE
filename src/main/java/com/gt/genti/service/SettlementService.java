package com.gt.genti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.User;
import com.gt.genti.dto.SettlementFindResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.SettlementRepository;
import com.gt.genti.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {
	private final SettlementRepository settlementRepository;
	private final UserRepository userRepository;
	public List<SettlementFindResponseDto> getAllSettlements(Long userId) {
		User foundUser = userRepository.findById(userId).orElseThrow(()-> new ExpectedException(ErrorCode.UserNotFound));
		List<Settlement> settlementList = settlementRepository.findAllByUserOrderByCreatedAtDesc(foundUser);
		return settlementList.stream().map(SettlementFindResponseDto::new).toList();

	}
}
