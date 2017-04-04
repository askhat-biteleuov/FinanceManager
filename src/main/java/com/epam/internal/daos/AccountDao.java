package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Account_;
import com.epam.internal.models.User;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountDao extends GenericDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

    public List<Account> findAllUserAccounts(User user) {
        Session currentSession = getSessionFactory().openSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Account> cq = builder.createQuery(Account.class);
        Root<Account> accountRoot = cq.from(Account.class);
        cq.where(builder.equal(accountRoot.get(Account_.user), user));
        List<Account> accounts = currentSession.createQuery(cq).getResultList();
        currentSession.close();
        return accounts;
    }

    public Account findUserAccountByName(User user, String name) {
        Session currentSession = getSessionFactory().openSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Account> cq = builder.createQuery(Account.class);
        Root<Account> accountRoot = cq.from(Account.class);
        Predicate p1 = builder.equal(accountRoot.get(Account_.user), user);
        Predicate p2 = builder.equal(accountRoot.get(Account_.name), name);
        cq.where(builder.and(p1,p2));
        Account account = currentSession.createQuery(cq).getSingleResult();
        currentSession.close();
        return account;
    }
}