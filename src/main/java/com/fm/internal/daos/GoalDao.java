package com.fm.internal.daos;

import com.fm.internal.models.Account;
import com.fm.internal.models.Account_;
import com.fm.internal.models.Goal;
import com.fm.internal.models.Goal_;
import com.fm.internal.models.Income;
import com.fm.internal.models.Income_;
import com.fm.internal.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class GoalDao extends GenericDao<Goal>{
    public GoalDao() {
        super(Goal.class);
    }

    @Transactional
    public List<Goal> getGoalsByUser(User user){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Goal> query = builder.createQuery(Goal.class);
        Root<Goal> root = query.from(Goal.class);
        Predicate equalUser = builder.equal(root.get(Goal_.user), user);
        query.where(equalUser);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Account> getGoalsWithoutIncomeForMonth(User user) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
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
        return getEntityManager().createQuery(query).getResultList();
    }
}
