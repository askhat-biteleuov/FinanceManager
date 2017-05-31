package com.fm.internal.daos;

import com.fm.internal.models.*;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class GoalDao extends GenericDao{
    public GoalDao() {
        super(Goal.class);
    }

    @Transactional
    public List<Goal> getGoalsByUser(User user){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Goal> query = builder.createQuery(Goal.class);
        Root<Goal> root = query.from(Goal.class);
        Predicate equalUser = builder.equal(root.get(Goal_.user), user);
        query.where(equalUser);
        return currentSession.createQuery(query).getResultList();
    }

    @Transactional
    public List<Account> getGoalsWithoutIncomeForMonth(User user) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Account> query = builder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        Join<Account, Income> incomes = root.join(Account_.incomes, JoinType.LEFT);
        Expression month = builder.function("month", Integer.class, incomes.get(Income_.date));
        Expression year = builder.function("year", Integer.class, incomes.get(Income_.date));
        Predicate notEqualMonth = builder.notEqual(month, LocalDate.now().getMonthValue());
        Predicate equalYear = builder.equal(year, LocalDate.now().getYear());
        Predicate equalUser = builder.equal(root.get(Account_.user), user);
        Predicate emptyIncomes = builder.isEmpty(root.get(Account_.incomes));
        query.where(builder.or(builder.and(equalUser, equalYear, notEqualMonth), builder.and(equalUser, emptyIncomes)));
        return currentSession.createQuery(query).getResultList();
    }
}
