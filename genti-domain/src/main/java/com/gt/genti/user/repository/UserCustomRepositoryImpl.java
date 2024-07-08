package com.gt.genti.user.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.gt.genti.user.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "user", cacheManager = "redisCacheManager")
    public Optional<User> findByIdWithCache(Long id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        User user = query.getSingleResult();
        return Optional.ofNullable(user);
    }
}

