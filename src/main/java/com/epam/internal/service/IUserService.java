package com.epam.internal.service;

import com.epam.internal.data.entities.User;
import com.epam.internal.data.entities.UserInfo;

import java.util.List;

public interface IUserService {

    User findById(int id);
    User findByEmail(String email);
    void createUser(User user);
    void deleteUserById(int id);
    void updateUser(User user);
    void updateUserInfo(User user, UserInfo info);

}
