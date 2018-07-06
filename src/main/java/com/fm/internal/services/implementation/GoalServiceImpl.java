package com.fm.internal.services.implementation;


import com.fm.internal.daos.GoalDao;
import com.fm.internal.dtos.GoalDto;
import com.fm.internal.models.Goal;
import com.fm.internal.models.User;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService{

    private final GoalDao goalDao;
    private CurrencyService currencyService;

    @Autowired
    public GoalServiceImpl(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    @Override
    public List<Goal> getGoalsByUser(User user) {
        return goalDao.getGoalsByUser(user);
    }

    @Override
    public List<Goal> getGoalsWithoutIncomeForMonth(User user) {
        List<Goal> goals = goalDao.getGoalsWithoutIncomeForMonth(user).stream().filter(account -> account instanceof Goal && !((Goal) account).isFinished() && !((Goal) account).isOverdue()).map(account -> (Goal) account).collect(Collectors.toList());
        return goals;
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

    @Override
    public void addGoal(GoalDto goalDto, User user) {
        Goal goal = new Goal(goalDto.getName(), new BigDecimal(0), new BigDecimal(goalDto.getGoalAmount()), null,
                user, currencyService.findCurrencyByCharCode(goalDto.getCurrency()), LocalDate.parse(goalDto.getDate()));
        goalDao.add(goal);
    }

    @Override
    public Goal getGoalById(long id){
        return goalDao.getById(id);
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
