package com.trading.service;

import com.trading.domain.VerificationType;
import com.trading.model.User;

public interface UserService {
    User findUserByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long userId) throws Exception;

    User enableTwoFactorAuth(VerificationType verificationType,
                             String sentTo,
                             User user);

    User updatePassword(User user, String newPassword);
}
