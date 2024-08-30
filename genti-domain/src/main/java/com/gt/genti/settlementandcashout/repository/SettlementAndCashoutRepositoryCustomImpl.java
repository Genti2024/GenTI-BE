package com.gt.genti.settlementandcashout.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class SettlementAndCashoutRepositoryCustomImpl implements SettlementAndCashoutRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Object[]> findSettlementAndCashoutByCreatorPagination(Long creatorId, Pageable pageable) {

		String queryStr = "(SELECT s.id as id , true as isSettlement, s.reward as amount, s.created_at as createdAt, " +
			"CASE WHEN c.id IS NULL THEN 'AVAILABLE' ELSE c.status END as status " +
			"FROM settlement s " +
			"LEFT OUTER JOIN cashout c ON c.creator_id = :creatorId "
			+ "LEFT JOIN picture_generate_response ON s.picture_generate_response_id = picture_generate_response.id " +
			"UNION ALL " +
			"SELECT w.id as id , false as isSettlement, w.amount as amount, w.created_at as createdAt, w.status " +
			"FROM cashout c " +
			"WHERE c.creator_id = :creatorId) " +
			"ORDER BY createdAt DESC";

		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("creatorId", creatorId);
		int totalRows = query.getResultList().size();
		query.setFirstResult((int)pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Object[]> resultList = query.getResultList();
		return new PageImpl<>(resultList, pageable, totalRows);
	}
}
