package com.fm.internal.daos;

import com.fm.internal.models.User;
import com.fm.internal.models.User_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.where(builder.equal(userRoot.get(User_.email), email));
        try {
            return getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}