package com.fm.internal.services;

import com.fm.internal.models.Goal;
import com.fm.internal.models.User;

import java.util.List;

public interface GoalService {
    List<Goal> getGoalsByUser(User user);

    void addGoal(Goal goal);

    void updateGoal(Goal goal);

    void deleteGoal(Goal goal);
}
