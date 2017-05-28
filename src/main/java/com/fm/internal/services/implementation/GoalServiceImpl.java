package com.fm.internal.services.implementation;


import com.fm.internal.daos.GoalDao;
import com.fm.internal.models.Goal;
import com.fm.internal.models.User;
import com.fm.internal.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GoalServiceImpl implements GoalService{
    @Autowired
    private GoalDao goalDao;

    @Override
    public List<Goal> getGoalsByUser(User user) {
        return goalDao.getGoalsByUser(user);
    }

    @Override
    public void addGoal(Goal goal) {
        goalDao.add(goal);
    }

    @Override
    public void updateGoal(Goal goal) {
        goalDao.update(goal);
    }

    @Override
    public void deleteGoal(Goal goal) {
        goalDao.delete(goal);
    }
}
