package com.gt.genti.other.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.DirectoryTraversalAttack;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryTraversalAttack, Long> {

	Optional<DirectoryTraversalAttack> findByUrl(String url);
}
