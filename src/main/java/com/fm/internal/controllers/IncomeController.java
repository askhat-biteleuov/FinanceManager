package com.fm.internal.controllers;

import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

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
        return new ModelAndView("newincome", "incomeDto", incomeDto);
    }

    @RequestMapping(value = "/addincome", method = RequestMethod.POST)
    public ModelAndView addIncome(@Valid @ModelAttribute("incomeDto") IncomeDto incomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (!result.hasErrors() && user != null) {
            Account account = accountService.findAccountById(incomeDto.getAccountId());
            if (account != null) {
                incomeService.addIncome(incomeDto, account);
                account.setBalance(account.getBalance().add(new BigDecimal(incomeDto.getAmount())));
                accountService.updateAccount(account);
                return new ModelAndView("redirect:" + "/index");
            }
        }
        return new ModelAndView("newincome");
    }

    @RequestMapping(value = "/income/list")
    public ModelAndView listOfIncomes(@RequestParam("accountId") int accountId) {
        ModelAndView modelAndView = new ModelAndView("incomes-list");
        Account accountById = accountService.findAccountById(accountId);
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(accountById);
        modelAndView.addObject("incomes", allIncomesInAccount);
        return modelAndView;
    }
}
