package com.fm.internal.services.implementation;


import com.fm.internal.daos.GoalDao;
import com.fm.internal.dtos.GoalDto;
import com.fm.internal.models.Goal;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.GoalService;
import com.fm.internal.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GoalServiceImpl implements GoalService{
    @Autowired
    private GoalDao goalDao;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private IncomeService incomeService;

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

    @Override
    public void addGoal(GoalDto goalDto, User user) {
        Goal goal = new Goal(goalDto.getName(), new BigDecimal(goalDto.getBalance()), new BigDecimal(goalDto.getGoalAmount()),
                null, user, currencyService.findCurrencyByCharCode(goalDto.getCurrency()));
        goalDao.add(goal);
        Income income = new Income(new BigDecimal(goalDto.getBalance()), LocalDate.now(), LocalTime.now(), goal);
        income.setNote("Start balance");
        incomeService.addIncome(income);
    }
}
