package com.fm.internal.daos;

import com.fm.internal.models.*;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutcomeTypeDao extends GenericDao<OutcomeType> {

    public OutcomeTypeDao() {
        super(OutcomeType.class);
    }

//    @Transactional
//    public List<Outcome> getOutcomesByType(OutcomeType outcomeType, int offset, int limit) {
//        Session session = getSessionFactory().getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
//        Root<Outcome> root = query.from(Outcome.class);
//        query.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
//        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
//    }

    @Transactional
    public List<OutcomeType> getAvailableOutcomeTypesByUser(User user) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<OutcomeType> query = builder.createQuery(OutcomeType.class);
        Root<OutcomeType> root = query.from(OutcomeType.class);
        Predicate userEquals = builder.equal(root.get(OutcomeType_.user), user);
        Predicate isAvailable = builder.isTrue(root.get(OutcomeType_.isAvailable));
        query.where(userEquals, isAvailable);
        return currentSession.createQuery(query).getResultList();
    }

    @Transactional
    public OutcomeType getOutcomeTypeByNameAndUser(User user, String name) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<OutcomeType> query = builder.createQuery(OutcomeType.class);
        Root<OutcomeType> root = query.from(OutcomeType.class);
        Predicate getOutcomeTypeByNameAndUser = builder.and(builder.equal(builder.lower(root.get(OutcomeType_.name)), name.toLowerCase()), builder.equal(root.get(OutcomeType_.user), user));
        query.where(getOutcomeTypeByNameAndUser);
        try {
            return currentSession.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<Outcome> getOutcomesByTypeByDate(OutcomeType outcomeType, int offset, int limit,
                                                 LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        Predicate equalDate = criteriaBuilder.between(root.get(Outcome_.date), start, end);
        Predicate equalOutcomeType = criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType);
        query.where(criteriaBuilder.and(equalDate, equalOutcomeType));
        return session.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public Long getOutcomesNumberByTypeByDate(OutcomeType outcomeType, LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.count(root));
        Predicate equalDate = criteriaBuilder.between(root.get(Outcome_.date), start, end);
        Predicate equalOutcomeType = criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType);
        query.where(criteriaBuilder.and(equalDate, equalOutcomeType));
        return session.createQuery(query).uniqueResult();
    }

    @Transactional
    public Map<Integer, Map<String, Double>> countOutcomeTypesValueByMonth(Account account, LocalDate start) {
        Map<Integer, Map<String, Double>> outcomeTypesValueByDay = new HashMap<>();
//        for (int i = 1; i <= start.lengthOfMonth(); i++) {
//            outcomeTypesValueByDay.put(i, null);
//        }
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        query.multiselect(outcomeRoot.get(Outcome_.outcomeType), criteriaBuilder.sum(outcomeRoot.get(Outcome_.amount)));
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        for (int i = 1; i <= start.lengthOfMonth(); i++) {
            Predicate equalDate = criteriaBuilder.equal(outcomeRoot.get(Outcome_.date),
                    LocalDate.of(start.getYear(), start.getMonth(), i));
            query.where(equalAccount, equalDate);
            query.groupBy(outcomeRoot.get(Outcome_.outcomeType));
            List<Object[]> valueArray = session.createQuery(query).getResultList();
            Map<String, Double> outcomeTypesValue = new HashMap<>();
            if (valueArray.size() > 0) {
                for (Object[] values : valueArray) {
                    final OutcomeType outcomeType = (OutcomeType) values[0];
                    final BigDecimal value = (BigDecimal) values[1];
                    outcomeTypesValue.put(outcomeType.getName(), value.doubleValue());
                }
            }
            outcomeTypesValueByDay.put(i, outcomeTypesValue);
        }
        return outcomeTypesValueByDay;
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
        List<Object[]> valueArray = session.createQuery(query).getResultList();
        for (Object[] values : valueArray) {
            final OutcomeType outcomeType = (OutcomeType) values[0];
            final BigDecimal value = (BigDecimal) values[1];
            outcomeTypesValue.put(outcomeType.getName(), value.doubleValue());
        }
        return outcomeTypesValue;
    }

    @Transactional
    public BigDecimal getSumOfOutcomesInTypeForMonth(OutcomeType outcomeType) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.sum(root.get(Outcome_.defaultAmount)));
        Expression month = criteriaBuilder.function("month", Integer.class, root.get(Outcome_.date));
        Expression year = criteriaBuilder.function("year", Integer.class, root.get(Outcome_.date));
        Predicate equalMonth = criteriaBuilder.equal(month, LocalDate.now().getMonthValue());
        Predicate equalYear = criteriaBuilder.equal(year, LocalDate.now().getYear());
        Predicate equalType = criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType);
        query.where(equalType, equalMonth, equalYear);
        query.groupBy(root.get(Outcome_.outcomeType));
        try {
            BigDecimal sum = session.createQuery(query).getSingleResult();
            return sum;
        } catch (NoResultException e) {
            return BigDecimal.valueOf(0);
        }
    }
}
