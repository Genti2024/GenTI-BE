package com.gt.genti.user.service.validator;

import java.util.Optional;

import com.gt.genti.user.model.User;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

public class UserValidator {

    public static void validateUserAuthorization(final Long userEntityId, final Long userId) {
        if (!userEntityId.equals(userId)) {
            throw ExpectedException.withLogging(ResponseCode.Forbidden);
        }
    }

    public static boolean hasChange(final Object object) {
        return (object != null);
    }

    public static boolean isNewUser(final Optional<User> user) {
        return user.isEmpty();
    }

}
