package com.gt.genti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCompleted;

public interface PictureRepository extends JpaRepository<PictureCompleted, Long> {
}
