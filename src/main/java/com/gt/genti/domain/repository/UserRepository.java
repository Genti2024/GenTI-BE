package com.gt.genti.domain.repository;

import java.util.Optional;

import com.gt.genti.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
