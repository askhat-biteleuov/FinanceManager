package com.epam.internal.services;

import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;

import javax.servlet.http.HttpSession;

public interface UserService {

    User findByEmail(String email);

    void createUser(User user);

    void deleteUserByEmail(String email);

    void deleteUser(User user);

    void updateUser(User user);

    void updateUserInfo(User user, UserInfo info);
}
