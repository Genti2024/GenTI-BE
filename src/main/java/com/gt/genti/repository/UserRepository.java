package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query(value = "select u "
		+ "from User u "
		+ "where u.userRole = com.gt.genti.domain.enums.UserRole.ADMIN ")
	List<User> findAdminUser(Pageable pageable);

	@NotNull
	Page<User> findAll(@NotNull Pageable pageable);

	Page<User> findAllByUserRole(Pageable pageable, UserRole userRole);
}
