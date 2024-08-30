package com.gt.genti.settlementandcashout.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SettlementAndCashoutRepositoryCustom {
	Page<Object[]> findSettlementAndCashoutByCreatorPagination(Long creatorId, Pageable pageable);
}
