package com.gt.genti.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.user.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findUserBySocialId(String socialId);
    @Query("SELECT u FROM User u WHERE u.deletedAt < :currentDate")
    List<User> findIdByDeletedAtBefore(LocalDateTime currentDate);

    @Query(value = "select u "
        + "from User u "
        + "where u.userRole = com.gt.genti.user.model.UserRole.ADMIN ")
    List<User> findAdminUser(Pageable pageable);


}

