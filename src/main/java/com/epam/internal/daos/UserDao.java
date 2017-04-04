package com.epam.internal.daos;

import com.epam.internal.models.User;
import com.epam.internal.models.User_;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User getUserByEmail(String email) {
        Session currentSession = getSessionFactory().openSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.where(builder.equal(userRoot.get(User_.email), email));
        User user = currentSession.createQuery(cq).getSingleResult();
        currentSession.close();
        return user;
    }

      /*User user = (User) currentSession
                .createQuery("from User u where u.email=:email")
                .setParameter("email", email)
                .uniqueResult();
        currentSession.close();*/
        /*CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<RuleVar> ruleVariableRoot = query.from(RuleVar.class);
        query.select(ruleVariableRoot.get(RuleVar_.varType)).distinct(true);*/
        }