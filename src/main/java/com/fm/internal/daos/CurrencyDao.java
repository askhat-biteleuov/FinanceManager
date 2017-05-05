package com.fm.internal.daos;

import com.fm.internal.models.Currency;
import com.fm.internal.models.Currency_;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CurrencyDao extends GenericDao<Currency> {
    public CurrencyDao() {
        super(Currency.class);
    }

    @Transactional
    public Currency getByCharCode(String charCode) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Currency> cq = builder.createQuery(Currency.class);
        Root<Currency> root = cq.from(Currency.class);
        cq.where(builder.equal(root.get(Currency_.characterCode), charCode));
        try {
            return currentSession.createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
