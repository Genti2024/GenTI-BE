package com.gt.genti.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.PictureGenerateRequest;

@Repository
public interface PictureGenerateRequestRepository extends JpaRepository<PictureGenerateRequest, Long> {
}
