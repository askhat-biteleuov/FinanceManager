package com.epam.internal.daos;

import com.epam.internal.models.User;
import com.epam.internal.models.User_;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.where(builder.equal(userRoot.get(User_.email), email));
        try {
            return currentSession.createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}