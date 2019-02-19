package com.fm.internal.controllers;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import com.fm.internal.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private OutcomeTypeService typeService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private GoalService goalService;

    @RequestMapping(value = {"/index", "/"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("outcometypeDto", new OutcomeTypeDto());
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null) {
            modelAndView.addObject("user", loggedUser);
            Map<OutcomeType, BigDecimal> outcomeTypes = new TreeMap<>(Comparator.comparing(OutcomeType::getName));
            loggedUser.getOutcomeTypes().stream().filter(OutcomeType::isAvailable).forEach(outcomeType -> {
                BigDecimal sumOfOutcomesInTypeForMonth = typeService.getSumOfOutcomesInTypeForMonth(outcomeType);
                outcomeTypes.put(outcomeType, sumOfOutcomesInTypeForMonth);
            });
            modelAndView.addObject("accounts", accountService.findAllUserAccounts(loggedUser));
            modelAndView.addObject("hashtags",hashTagService.getHashTagsByUser(loggedUser));
            modelAndView.addObject("outcomeTypes", outcomeTypes);
            modelAndView.addObject("currencies", currencyService.getCurrencies());
            modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser()));
            modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
            modelAndView.addObject("goalsMessages", goalService.getGoalsWithoutIncomeForMonth(loggedUser));
        }
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView init() {
        return new ModelAndView("login");
    }
}
