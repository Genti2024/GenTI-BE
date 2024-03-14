package com.gt.genti.generate.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureGenerateRequestRepository extends JpaRepository<PictureGenerateRequest, Long> {
}
