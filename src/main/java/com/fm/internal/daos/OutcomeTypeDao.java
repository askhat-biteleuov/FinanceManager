package com.fm.internal.daos;

import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.Outcome_;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class OutcomeTypeDao extends GenericDao<OutcomeType> {

    public OutcomeTypeDao() {
        super(OutcomeType.class);
    }

    @Transactional
    public List<Outcome> getOutcomesByType(OutcomeType outcomeType, int offset, int limit) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public Long getOutcomesNumberByType(OutcomeType outcomeType) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.count(root));
        query.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
        return session.createQuery(query).uniqueResult();
    }

    @Transactional
    public Map<String, Double> countOutcomeTypesValueByDate(Account account, LocalDate start, LocalDate end) {
        Map<String, Double> outcomeTypesValue = new HashMap<>();
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        query.multiselect(outcomeRoot.get(Outcome_.outcomeType), criteriaBuilder.sum(outcomeRoot.get(Outcome_.amount)));
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        Predicate equalDate = criteriaBuilder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(equalAccount, equalDate);
        query.groupBy(outcomeRoot.get(Outcome_.outcomeType));
        List<Object[]> valueArray = session.createQuery( query ).getResultList();
        for ( Object[] values : valueArray ) {
            final OutcomeType outcomeType = (OutcomeType) values[0];
            final BigDecimal value = (BigDecimal) values[1];
            outcomeTypesValue.put(outcomeType.getName(), value.doubleValue());
        }
        return outcomeTypesValue;
    }
}
