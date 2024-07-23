package com.gt.genti.picturegenerateresponse.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

public interface PictureGenerateResponseRepository extends JpaRepository<PictureGenerateResponse, Long> {
	List<PictureGenerateResponse> findAllByCreatedAtBeforeAndStatusIs(LocalDateTime expireThreshold, PictureGenerateResponseStatus status);
}
