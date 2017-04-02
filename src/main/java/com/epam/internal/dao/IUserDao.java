package com.epam.internal.dao;

import com.epam.internal.data.entities.User;

public interface IUserDao {

    void saveUser(User user);

    void deleteUser(User user);

    void updateUser(User user);

    User findUserByEmail(String email);
}
