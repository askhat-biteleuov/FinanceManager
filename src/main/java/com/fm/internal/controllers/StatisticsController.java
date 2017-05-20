package com.fm.internal.controllers;

import com.fm.internal.daos.OutcomeTypeDao;
import com.fm.internal.dtos.StatisticsDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.OutcomeService;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class StatisticsController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView getStatPage(@ModelAttribute("statisticsDto") StatisticsDto statisticsDto) {
        User user = userService.getLoggedUser();
        ModelAndView modelAndView = new ModelAndView("statistics");
        modelAndView.addObject("accounts", accountService.findAllUserAccounts(user));
        return modelAndView;
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    public Object getLineChartJson(@RequestBody StatisticsDto statisticsDto, BindingResult result) {
        Account accountByName = accountService.findUserAccountByName(userService.getLoggedUser(), statisticsDto.getAccountName());
        String date = statisticsDto.getYear()+"-"+statisticsDto.getMonth()+"-"+"01";
        return outcomeTypeService.countOutcomeTypesValueByMonth(accountByName, LocalDate.parse(date));
    }
}
