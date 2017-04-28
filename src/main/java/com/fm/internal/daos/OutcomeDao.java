package com.fm.internal.daos;

import com.fm.internal.models.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class OutcomeDao extends GenericDao<Outcome> {

    public OutcomeDao() {
        super(Outcome.class);
    }

    @Transactional
    public List<Outcome> getAccountOutcomes(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getUserOutcomes(User user){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, Account> accountJoin = outcomeRoot.join(Outcome_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Outcome_.date)));
        query.where(builder.equal(accountJoin.get(Account_.user), user));
        return currentSession.createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getAccountOutcomesByDate(Account account, LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        Predicate equalDate = criteriaBuilder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public Long getAccountOutcomeAmount(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.count(root));
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        return session.createQuery(query).uniqueResult();
    }

    @Transactional
    public List<Outcome> getOutcomesPage(Account account, int offset, int limit) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public void deleteOutcomeByType(OutcomeType outcomeType) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<Outcome> delete = criteriaBuilder.createCriteriaDelete(Outcome.class);
        Root<Outcome> root = delete.from(Outcome.class);
        delete.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
        session.createQuery(delete).executeUpdate();
    }

    @Transactional
    public void updateOutcomeByType(OutcomeType oldOutcomeType, OutcomeType newOutcomeType) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Outcome> update = criteriaBuilder.createCriteriaUpdate(Outcome.class);
        Root<Outcome> root = update.from(Outcome.class);
        update.set(Outcome_.outcomeType, newOutcomeType);
        update.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), oldOutcomeType));
        session.createQuery(update).executeUpdate();
    }
}