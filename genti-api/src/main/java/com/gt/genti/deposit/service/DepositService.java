package com.gt.genti.deposit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.deposit.repository.DepositRepository;
import com.gt.genti.user.model.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepositService {
	private final DepositRepository depositRepository;

	public Deposit createDeposit(User user) {
		Deposit newDeposit = new Deposit(user);
		return depositRepository.save(newDeposit);
	}
}
