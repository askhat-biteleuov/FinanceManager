package com.fm.internal.controllers;

import com.fm.internal.currency.scheduler.GetCurrencyTask;
import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OutcomeTypeService typeService;
    @Autowired
    private GetCurrencyTask currencyTask;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView init() {
        return new ModelAndView("login");
    }

    @RequestMapping({"/index", "/"})
    public ModelAndView index() {
        currencyTask.execute();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("outcometypeDto", new OutcomeTypeDto());
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null) {
            modelAndView.addObject("user", loggedUser);
            Map<OutcomeType, BigDecimal> outcomeTypes = new TreeMap<>(Comparator.comparing(OutcomeType::getName));
            for (OutcomeType outcomeType : loggedUser.getOutcomeTypes()) {
                outcomeTypes.put(outcomeType, typeService.getSumOfOutcomesInTypeForMonth(outcomeType));
            }
            modelAndView.addObject("outcomeTypes", outcomeTypes);

        }
        return modelAndView;
    }

}
