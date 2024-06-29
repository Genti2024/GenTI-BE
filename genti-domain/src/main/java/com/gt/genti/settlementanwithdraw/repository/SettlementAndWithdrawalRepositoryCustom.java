package com.gt.genti.settlementanwithdraw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SettlementAndWithdrawalRepositoryCustom {
	Page<Object[]> findSettlementAndWithdrawByCreatorPagination(Long creatorId, Pageable pageable);
}
