package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.genti.domain.ResponseExample;

public interface ResponseExampleRepository extends JpaRepository<ResponseExample, Long> {
	List<ResponseExample> findAllByPromptOnlyIsFalse();
}