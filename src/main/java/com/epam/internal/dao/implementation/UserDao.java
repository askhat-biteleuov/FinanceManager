package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.IUserDao;
import com.epam.internal.data.entities.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends AbstractDao<String, User> implements IUserDao {

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


}
