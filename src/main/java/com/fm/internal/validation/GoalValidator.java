package com.fm.internal.validation;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.GoalDto;
import com.fm.internal.models.Goal;
import com.fm.internal.services.GoalService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class GoalValidator implements Validator {

    @Autowired
    private GoalService goalService;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GoalDto goalDto = (GoalDto) o;
        List<Goal> goals = goalService.getGoalsByUser(userService.getLoggedUser());
        for (Goal goal : goals) {
            if (goal.getName().equalsIgnoreCase(goalDto.getName())) {
                errors.rejectValue("name", "Exist");
            }
        }
    }
}
