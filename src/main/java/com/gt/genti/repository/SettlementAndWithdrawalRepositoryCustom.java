package com.gt.genti.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.dto.creator.response.SettlementAndWithdraw;

public interface SettlementAndWithdrawalRepositoryCustom {
	Page<Object[]> findSettlementAndWithdrawByCreatorPagination(Long creatorId, Pageable pageable);
}
