package com.gt.genti.user.repository;

import java.util.Optional;

import com.gt.genti.user.model.User;

public interface UserCustomRepository {

    Optional<User> findByIdWithCache(Long id);

}
