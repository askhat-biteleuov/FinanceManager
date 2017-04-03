package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.UserDao;
import com.epam.internal.data.entities.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<String, User> implements UserDao {

    @Override
    public void saveUser(User user) {
        create(user);
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return getByKey(email);
    }

/*
    public User getUserByEmail(String email) {
        User user = (User) getCurrentSession()
                    .createQuery("from User u where u.info.email=:email")
                    .setParameter("email", email)
                    .uniqueResult();
        return user;
}*/
}
