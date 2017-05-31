package com.fm.internal.services;

import com.fm.internal.dtos.GoalDto;
import com.fm.internal.models.Goal;
import com.fm.internal.models.User;

import java.util.List;

public interface GoalService {
    List<Goal> getGoalsByUser(User user);

    List<Goal> getGoalsWithoutIncomeForMonth(User user);

    void addGoal(Goal goal);

    void updateGoal(Goal goal);

    void deleteGoal(Goal goal);

    void addGoal(GoalDto goalDto, User loggedUser);
}
