package com.epam.internal.controllers;

import com.epam.internal.models.*;
import com.epam.internal.services.IncomeService;
import com.epam.internal.services.OutcomeService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.implementation.AccountServiceImpl;
import com.epam.internal.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class Welcome {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @Autowired
    private OutcomeService outcomeService;

    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome() {
        User user = new User("user@email","password",new UserInfo("name","lastname"));
        userService.createUser(user);
        userService.deleteUser(user);
        User user2 = new User("user2@email","password",new UserInfo("name","lastname"));
        userService.createUser(user2);
//        User user2 = new User("user2@email","password",new UserInfo("namee","lastnamee"));
//        userService.createUser(user2);
//        Account account3 = new Account("visa", new BigDecimal(23243), null, user2);
//        accountService.createAccount(account3);
//        Account account = new Account("visa", new BigDecimal(2323), null, user);
//        accountService.createAccount(account);
//        Account account2 = new Account("mastercard", new BigDecimal(2343), null, user);
//        accountService.createAccount(account2);
//        userService.deleteUser(user);
//        accountService.deleteAccount(accountService.findUserAccountByName(user2, "visa"));
        return "success";
    }

    @RequestMapping("/accounts")
    public ModelAndView users() {
        ModelAndView model = new ModelAndView("accounts");
        User user = new User("user@email","password",new UserInfo("name","lastname"));
        userService.createUser(user);
        Account account = new Account("visa", new BigDecimal(2323), null, user);
        accountService.createAccount(account);
        Account account2 = new Account("mastercard", new BigDecimal(2343), null, user);
        accountService.createAccount(account2);
        model.addObject("accounts", accountService.findAllUserAccounts(user));
        return model;
    }

    @RequestMapping("/income")
    @ResponseBody
    public String income() {
        User user = new User("user@email","password",new UserInfo("name","lastname"));
        Account account = new Account("card", BigDecimal.valueOf(5000), null, user);
        Income income = new Income(BigDecimal.valueOf(300), new Date(), account);
        incomeService.addIncome(income);
        return incomeService.findById(1L).getAmount().toString();
    }

    @RequestMapping("/inout")
    public ModelAndView types() {
        ModelAndView view = new ModelAndView("inoutcomes");
        User user = userService.findByEmail("user@email");
        List<Account> allUserAccounts = accountService.findAllUserAccounts(user);
        Account account = allUserAccounts.get(0);
        Income income = new Income(BigDecimal.valueOf(40000), new Date(), account);
        OutcomeType food = new OutcomeType("food", BigDecimal.valueOf(5000), user);
        OutcomeType cinema = new OutcomeType("cinema", BigDecimal.valueOf(1500), user);
        Outcome outcomeFood = new Outcome(BigDecimal.valueOf(300), new Date(), account, food);
        Outcome outcomeCinema = new Outcome(BigDecimal.valueOf(900), new Date(), account, cinema);
        incomeService.addIncome(income);
        outcomeTypeService.addOutcomeType(food);
        outcomeTypeService.addOutcomeType(cinema);
        outcomeService.addOutcome(outcomeFood);
        outcomeService.addOutcome(outcomeCinema);
        view.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(user));
        view.addObject("outcomes", outcomeService.getAllOutcomes(account));
        view.addObject("incomes", incomeService.findAllIncomesInAccount(account));
        return view;
    }
}