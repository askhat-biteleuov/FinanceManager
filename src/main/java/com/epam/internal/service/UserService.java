package com.epam.internal.service;

import com.epam.internal.data.entities.User;
import com.epam.internal.data.entities.UserInfo;

import java.util.List;

public interface UserService {

    User findByEmail(String email);
    void createUser(User user);
    void deleteUserByEmail(String email);
    void updateUser(User user);
    void updateUserInfo(User user, UserInfo info);

}
