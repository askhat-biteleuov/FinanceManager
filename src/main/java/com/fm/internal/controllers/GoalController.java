package com.fm.internal.controllers;

import com.fm.internal.dtos.GoalDto;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import com.fm.internal.validation.GoalValidator;
import com.fm.internal.validation.util.ValidErrors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/goal")
public class GoalController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private GoalValidator goalValidator;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getGoal(@RequestParam(required = false) Long goalId) {
        User loggedUser = userService.getLoggedUser();
        ModelAndView modelAndView = new ModelAndView();
        if (goalId != null) {
            //show goal(outcomes, incomes)
            modelAndView.setViewName("goal");
        } else {
            modelAndView.addObject("currencies", currencyService.getCurrencies());
            modelAndView.addObject("goals", goalService.getGoalsByUser(loggedUser));
            modelAndView.setViewName("goal-list");
        }
        modelAndView.addObject("hashtags", hashTagService.getHashTagsByUser(loggedUser));
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addGoal(@Valid @RequestBody GoalDto goalDto, BindingResult result) {
        goalValidator.validate(goalDto, result);
        User loggedUser = userService.getLoggedUser();
        if (result.hasErrors() || loggedUser == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        goalService.addGoal(goalDto, loggedUser);
        LOGGER.info("New account was added:" + goalDto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
