package com.fm.internal.daos;

import com.fm.internal.models.*;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class IncomeDao extends GenericDao<Income> {

    public IncomeDao() {
        super(Income.class);
    }

    @Transactional
    public List<Income> getAccountIncomes(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);
        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getAccountIncomesByDate(Account account, LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalDate = criteriaBuilder.between(incomeRoot.get(Income_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getIncomesPage(Account account, int offset, int limit) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);
        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Income> getUserIncomes(User user){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Income_.date)));
        query.where(builder.equal(accountJoin.get(Account_.user), user));
        return currentSession.createQuery(query).getResultList();
    }

    @Transactional
    public Long getUserIncomesNumberByDate(User user, LocalDate start, LocalDate end){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        query.select(builder.count(incomeRoot));
        Predicate equalDate = builder.between(incomeRoot.get(Income_.date), start, end);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        query.where(builder.and(equalDate,equalUser));
        return currentSession.createQuery(query).uniqueResult();
    }

    @Transactional
    public Long getAccountIncomeNumberByDate(Account account, LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Income> incomeRoot = query.from(Income.class);
        query.select(criteriaBuilder.count(incomeRoot));
        Predicate equalDate = criteriaBuilder.between(incomeRoot.get(Income_.date), start, end);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return session.createQuery(query).uniqueResult();
    }

//    No need of this method
//    @Transactional
//    public List<Income> getUserIncomesPage(User user, int offset, int limit){
//        Session currentSession = getSessionFactory().getCurrentSession();
//        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
//        CriteriaQuery<Income> query = builder.createQuery(Income.class);
//        Root<Income> incomeRoot = query.from(Income.class);
//        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
//        query.orderBy(builder.asc(accountJoin.getParent().get(Income_.date)));
//        query.where(builder.equal(accountJoin.get(Account_.user), user));
//        return currentSession.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
//    }

    @Transactional
    public List<Income> getUserIncomesPageByDate(User user, int offset, int limit, LocalDate start, LocalDate end){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Income_.date)));
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Predicate equalDate = builder.between(incomeRoot.get(Income_.date), start, end);
        query.where(builder.and(equalUser,equalDate));
        return currentSession.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Income> getAccountIncomesPageByDate(Account account, int offset, int limit, LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalDate = criteriaBuilder.between(incomeRoot.get(Income_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

}
