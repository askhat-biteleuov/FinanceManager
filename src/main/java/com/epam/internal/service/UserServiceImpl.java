package com.epam.internal.service;

import com.epam.internal.dao.UserDao;
import com.epam.internal.data.entities.User;
import com.epam.internal.data.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao dao;

    @Override
    public User findById(int id) {
        return dao.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return dao.findUserByEmail(email);
    }

    @Override
    public void createUser(User user) {
        dao.create(user);
    }

    @Override
    public void deleteUserById(int id) {
        dao.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void updateUserInfo(User user, UserInfo info) {
        user.setInfo(info);
        dao.update(user);
    }


}
