package com.gt.genti.user.service.validator;

import java.util.Optional;

import com.gt.genti.user.model.User;

public class UserValidator {

    public static boolean hasChange(final Object object) {
        return (object != null);
    }

    public static boolean isNewUser(final Optional<User> user) {
        return user.isEmpty();
    }

}
