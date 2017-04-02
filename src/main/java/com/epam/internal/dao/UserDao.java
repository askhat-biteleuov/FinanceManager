package com.epam.internal.dao;

import com.epam.internal.data.entities.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends AbstractDao<User> implements IGenericDao<User> {
    public User findUserByEmail(String email) {
        return getCurrentSession().get(User.class, email);
    }
}
