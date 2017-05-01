package com.fm.internal.daos;

import com.fm.internal.models.Account;
import com.fm.internal.models.Account_;
import com.fm.internal.models.User;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class AccountDao extends GenericDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

    @Transactional
    public BigDecimal getSumOfAllBalancesOfAccounts(User user) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Account> root = query.from(Account.class);
        query.select(criteriaBuilder.sum(root.get(Account_.balance)));
        query.where(criteriaBuilder.equal(root.get(Account_.user), user));
        query.groupBy(root.get(Account_.user));
        try {
            return session.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return BigDecimal.valueOf(0);
        }
    }
}