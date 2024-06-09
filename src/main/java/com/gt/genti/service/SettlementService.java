package com.gt.genti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.User;
import com.gt.genti.dto.SettlementFindResponseDto;
import com.gt.genti.repository.SettlementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {
	private final SettlementRepository settlementRepository;

	public List<SettlementFindResponseDto> getAllSettlements(User user) {
		List<Settlement> settlementList = settlementRepository.findAllByUserOrderByCreatedAtDesc(user);
		return settlementList.stream().map(SettlementFindResponseDto::new).toList();

	}
}
