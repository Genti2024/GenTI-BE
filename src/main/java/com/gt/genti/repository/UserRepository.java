package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("select u "
		+ "from User u "
		+ "where u.userRole = com.gt.genti.domain.enums.UserRole.ADMIN "
		+ "limit 1 ")
	Optional<User> findAdminUser();
}
