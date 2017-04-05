package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.Income_;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class IncomeDao extends GenericDao<Income> {

    public IncomeDao() {
        super(Income.class);
    }

    public List<Income> getAccountsIncomes(Account account) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);

        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        List<Income> incomes = session.createQuery(query).getResultList();
        session.close();
        return incomes;
    }

    public List<Income> getIncomesInAccountByDate(Account account, Date date) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);

        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalDate = criteriaBuilder.equal(incomeRoot.get(Income_.date), date);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        List<Income> incomesByDate = session.createQuery(query).getResultList();
        session.close();
        return incomesByDate;
    }
}
