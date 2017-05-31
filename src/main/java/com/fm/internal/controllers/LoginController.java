package com.fm.internal.controllers;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import com.fm.internal.services.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private GoalService goalService;

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
            if(hashtag.toLowerCase().contains(str.toLowerCase())){
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
            loggedUser.getOutcomeTypes().stream().filter(OutcomeType::isAvailable).forEach(outcomeType -> {
                BigDecimal sumOfOutcomesInTypeForMonth = typeService.getSumOfOutcomesInTypeForMonth(outcomeType);
                outcomeTypes.put(outcomeType, sumOfOutcomesInTypeForMonth);
            });
            modelAndView.addObject("hashtags",hashTagService.getHashTagsByUser(loggedUser));
            modelAndView.addObject("outcomeTypes", outcomeTypes);
            modelAndView.addObject("currencies", currencyService.getCurrencies());
            modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser()));
            modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
            modelAndView.addObject("goalsMessages", goalService.getGoalsWithoutIncomeForMonth(loggedUser));
        }
        return modelAndView;
    }
}
