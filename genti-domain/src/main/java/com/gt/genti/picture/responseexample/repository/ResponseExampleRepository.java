package com.gt.genti.picture.responseexample.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.picture.responseexample.model.ResponseExample;

public interface ResponseExampleRepository extends JpaRepository<ResponseExample, Long> {
	List<ResponseExample> findAllByPromptOnlyIsFalse();
	Page<ResponseExample> findAllByPromptOnlyIsFalse(Pageable pageable);
}
