package com.epam.internal.dao.test;

import com.epam.internal.data.entities.User;
import org.hibernate.Session;

public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User getUserByEmail(String email) {
        Session currentSession = getSessionFactory().openSession();
        User user = (User) currentSession
                .createQuery("from User u where u.email=:email")
                .setParameter("email", email)
                .uniqueResult();
        currentSession.close();
        return user;
    }

}