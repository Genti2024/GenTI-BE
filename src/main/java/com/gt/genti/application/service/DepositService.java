package com.gt.genti.application.service;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.User;
import com.gt.genti.repository.DepositRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositService {
	private final DepositRepository depositRepository;

	public Deposit createDeposit(User user) {
		Deposit newDeposit = new Deposit(user);
		return depositRepository.save(newDeposit);
	}
}
