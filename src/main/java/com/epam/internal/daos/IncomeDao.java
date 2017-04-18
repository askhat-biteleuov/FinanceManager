package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.Income_;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class IncomeDao extends GenericDao<Income> {

    public IncomeDao() {
        super(Income.class);
    }

    @Transactional
    public List<Income> getAccountsIncomes(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);
        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getIncomesInAccountByDate(Account account, LocalDate date) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalDate = criteriaBuilder.equal(incomeRoot.get(Income_.date), date);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getIncomesInAccountByMonth(Account account, LocalDate date) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalYear = criteriaBuilder.equal(criteriaBuilder.function("year", Integer.class,
                incomeRoot.get(Income_.date)), date.getYear());
        Predicate equalMonth = criteriaBuilder.equal(criteriaBuilder.function("month", Integer.class,
                incomeRoot.get(Income_.date)), date.getMonth().getValue());
        query.where(criteriaBuilder.and(equalAccount, equalYear, equalMonth));
        return session.createQuery(query).getResultList();
    }
}
