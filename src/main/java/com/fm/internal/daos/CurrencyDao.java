package com.fm.internal.daos;

import com.fm.internal.models.Currency;
import com.fm.internal.models.Currency_;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CurrencyDao extends GenericDao<Currency> {
    public CurrencyDao() {
        super(Currency.class);
    }

    @Transactional
    public Currency getByCharCode(String charCode) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Currency> cq = builder.createQuery(Currency.class);
        Root<Currency> root = cq.from(Currency.class);
        cq.where(builder.equal(root.get(Currency_.characterCode), charCode));
        try {
            return getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
