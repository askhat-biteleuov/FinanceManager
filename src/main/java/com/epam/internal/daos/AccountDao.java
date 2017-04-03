package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Account_;
import com.epam.internal.models.User;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountDao extends GenericDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

    public List<Account> findAllUserAccounts(User user) {
        Session currentSession = getSessionFactory().openSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Account> accountCriteriaQuery = builder.createQuery(Account.class);
        Root<Account> accountRoot = accountCriteriaQuery.from(Account.class);
        accountCriteriaQuery.where(builder.equal(accountRoot.get(Account_.user), user));
        List<Account> accounts = currentSession.createQuery(accountCriteriaQuery).getResultList();
        currentSession.close();
        return accounts;
    }
}