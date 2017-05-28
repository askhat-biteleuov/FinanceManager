package com.fm.internal.daos;

import com.fm.internal.models.Goal;
import com.fm.internal.models.Goal_;
import com.fm.internal.models.User;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
}
