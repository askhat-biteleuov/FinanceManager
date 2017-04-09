package com.epam.internal.controllers;

import com.epam.internal.dtos.IncomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.IncomeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class IncomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IncomeService incomeService;

    @RequestMapping(value = "/addincome", method = RequestMethod.GET)
    public ModelAndView newIncome(WebRequest request) {
        String accountId = request.getParameter("accountId");
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAccountId(Long.parseLong(accountId));
        ModelAndView modelAndView = new ModelAndView("newincome", "incomeDto", incomeDto);
        modelAndView.getModel().put("accountId", Long.parseLong(accountId));
        return modelAndView;
    }

    @RequestMapping(value = "/addincome", method = RequestMethod.POST)
    public ModelAndView addIncome(@Valid @ModelAttribute("incomeDto") IncomeDto incomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (!result.hasErrors() && user != null) {
            Account account = accountService.findAccountById(incomeDto.getAccountId());
            if (account != null) {
                incomeService.addIncome(incomeDto, account);
                return new ModelAndView("redirect:" + "/index");
            }
        }
        return new ModelAndView("newincome");
    }
}
