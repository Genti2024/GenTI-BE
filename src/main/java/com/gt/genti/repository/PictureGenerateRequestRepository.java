package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;

@Repository
public interface PictureGenerateRequestRepository
	extends JpaRepository<PictureGenerateRequest, Long>, PictureGenerateRequestRepositoryCustom {

	List<PictureGenerateRequest> findByRequestStatusIsActiveAndUserId_JPQL(Long userId);

	List<PictureGenerateRequest> findAllByRequester(User requester);
}
