package com.fm.internal.controllers;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.StatusBarService;
import com.fm.internal.services.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private OutcomeTypeService typeService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView init() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/hashtag", method = RequestMethod.GET)
    @ResponseBody
    public Object getHashtag(@RequestParam String str){
        List<String> hashtags = new ArrayList<>();
        hashtags.add("Java");
        hashtags.add("James");
        hashtags.add("AOP");
        hashtags.add("Dependency Injection");
        hashtags.add("Intuition");

        List <String> subListHashtags = new ArrayList<>();
        for (String hashtag : hashtags) {
            if(hashtag.contains(str)){
                subListHashtags.add(hashtag);
            }
        }
        return new ResponseEntity<>(subListHashtags,HttpStatus.OK);
    }


    @RequestMapping({"/index", "/"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("outcometypeDto", new OutcomeTypeDto());
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null) {
            modelAndView.addObject("user", loggedUser);
            Map<OutcomeType, BigDecimal> outcomeTypes = new TreeMap<>(Comparator.comparing(OutcomeType::getName));
            for (OutcomeType outcomeType : loggedUser.getOutcomeTypes()) {
                BigDecimal sumOfOutcomesInTypeForMonth = typeService.getSumOfOutcomesInTypeForMonth(outcomeType);
                outcomeTypes.put(outcomeType, sumOfOutcomesInTypeForMonth);
            }
            modelAndView.addObject("outcomeTypes", outcomeTypes);
            modelAndView.addObject("currencies", currencyService.getCurrencies());
            modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser()));
            modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
        }
        return modelAndView;
    }
}
