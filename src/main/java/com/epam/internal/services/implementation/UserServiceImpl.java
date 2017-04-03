//package com.epam.internal.services.implementation;
//
//import com.epam.internal.daos.UserDao;
//import com.epam.internal.models.User;
//import com.epam.internal.models.UserInfo;
//import com.epam.internal.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service("userService")
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserDao dao;
//
//    @Override
//    public User findByEmail(String email) {
//        return dao.findUserByEmail(email);
//    }
//
//    @Override
//    public void createUser(User user) {
//        dao.saveUser(user);
//    }
//
//    @Override
//    public void deleteUserByEmail(String email) {
//        dao.deleteUser(findByEmail(email));
//    }
//
//    @Override
//    public void updateUser(User user) {
//        dao.updateUser(user);
//    }
//
//    @Override
//    public void updateUserInfo(User user, UserInfo info) {
//        user.setInfo(info);
//        dao.updateUser(user);
//    }
//
//
//}
