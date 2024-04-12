package com.gt.genti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
