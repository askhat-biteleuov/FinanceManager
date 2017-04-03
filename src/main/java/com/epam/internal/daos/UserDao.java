package com.epam.internal.daos;

import com.epam.internal.models.User;
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