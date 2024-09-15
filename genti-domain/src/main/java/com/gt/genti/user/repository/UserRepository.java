package com.gt.genti.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;

public interface UserRepository extends UserJpaRepository, UserCustomRepository {

	List<User> findAdminUser(Pageable pageable);

	Page<User> findAllByUserRole(Pageable pageable, UserRole userRole);

	//TODO user redis 저장시 오류 발생으로 redis에 저장하지않도록 하고 추후 개선
	// edited at 2024-07-17
	// author 서병렬
	Optional<User> findByIdWithCache(Long id);

	Optional<User> findByEmail(String email);

	List<User> findUpdatePushTargetUsers();
}
