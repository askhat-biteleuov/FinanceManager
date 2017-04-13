package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Account_;
import com.epam.internal.models.User;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountDao extends GenericDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

    @Transactional(readOnly = true)
    public List<Account> findAllUserAccounts(User user) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Account> cq = builder.createQuery(Account.class);
        Root<Account> accountRoot = cq.from(Account.class);
        cq.where(builder.equal(accountRoot.get(Account_.user), user));
        return currentSession.createQuery(cq).getResultList();
    }

    @Transactional(readOnly = true)
    public Account findUserAccountByName(User user, String name) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Account> cq = builder.createQuery(Account.class);
        Root<Account> accountRoot = cq.from(Account.class);
        Predicate p1 = builder.equal(accountRoot.get(Account_.user), user);
        Predicate p2 = builder.equal(accountRoot.get(Account_.name), name);
        cq.where(builder.and(p1, p2));
        try {
            return currentSession.createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}