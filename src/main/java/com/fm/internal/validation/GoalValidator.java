package com.fm.internal.validation;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.GoalDto;
import com.fm.internal.models.Goal;
import com.fm.internal.services.GoalService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class GoalValidator implements Validator {

    private GoalService goalService;
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GoalDto goalDto = (GoalDto) o;
        List<Goal> goals = goalService.getGoalsByUser(userService.getLoggedUser());
        boolean anyMatch = false;
        if (goalDto.getId() != 0) {
            for (Goal goal : goals) {
                anyMatch = (goal.getId() != goalDto.getId()) && (goal.getName().equalsIgnoreCase(goalDto.getName()));
            }
        } else {
            anyMatch = goals.stream()
                    .map(Goal::getName)
                    .anyMatch(typeName -> typeName.equalsIgnoreCase(goalDto.getName()));
        }
        if (anyMatch) errors.rejectValue("name", "Exist");
    }

    @Autowired
    public void setGoalService(GoalService goalService) {
        this.goalService = goalService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
