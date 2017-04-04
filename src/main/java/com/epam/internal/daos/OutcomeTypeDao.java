package com.epam.internal.daos;

import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.OutcomeType_;
import com.epam.internal.models.User;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OutcomeTypeDao extends GenericDao<OutcomeType> {

    public OutcomeTypeDao() {
        super(OutcomeType.class);
    }

    public List<OutcomeType> getAllUsersOutcomeTypes(User user) {
        Session currentSession = getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<OutcomeType> query = criteriaBuilder.createQuery(OutcomeType.class);
        Root<OutcomeType> root = query.from(OutcomeType.class);

        query.where(criteriaBuilder.equal(root.get(OutcomeType_.user), user));
        List<OutcomeType> availableTypes = currentSession.createQuery(query).getResultList();
        currentSession.close();
        return availableTypes;
    }
}
