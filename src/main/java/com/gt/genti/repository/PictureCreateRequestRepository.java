package com.gt.genti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.PictureCreateRequest;

@Repository
public interface PictureCreateRequestRepository extends JpaRepository<PictureCreateRequest, Long> {
}
