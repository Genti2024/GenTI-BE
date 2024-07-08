package com.gt.genti.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;

public interface UserRepository extends UserJpaRepository, UserCustomRepository {
	Optional<User> findByEmail(String email);

	List<User> findAdminUser(Pageable pageable);

	Page<User> findAllByUserRole(Pageable pageable, UserRole userRole);

	Optional<User> findByIdWithCache(Long id);
}
