package com.gt.genti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.PictureCreated;

public interface PictureRepository extends JpaRepository<PictureCreated, Long> {
}
