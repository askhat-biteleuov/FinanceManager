package com.epam.internal.daos;

import com.epam.internal.models.Outcome;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.Outcome_;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OutcomeTypeDao extends GenericDao<OutcomeType> {

    public OutcomeTypeDao() {
        super(OutcomeType.class);
    }

    @Transactional
    public List<Outcome> getOutcomesOfType(OutcomeType outcomeType, int offset, int limit) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public Long getSizeOutcomesOfType(OutcomeType outcomeType) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.count(root));
        query.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
        return session.createQuery(query).uniqueResult();
    }
}
