package com.fm.internal.controllers;

import com.fm.internal.models.*;
import com.fm.internal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class InitController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/init")
    public ModelAndView init() {
        User user = new User("user@email", "password", new UserInfo("name", "lastname", currencyService.findCurrencyByCharCode("RUB")));
        userService.createUser(user);

        Account[] accounts = {
                new Account("Visa", new BigDecimal(29999), null, user, currencyService.findCurrencyByCharCode("KZT")),
                new Account("Mastercard", new BigDecimal(3000), null, user, currencyService.findCurrencyByCharCode("USD")),
                new Account("Альфа", new BigDecimal(5752), null, user, currencyService.findCurrencyByCharCode("EUR"))
        };
        for (Account account : accounts) {
            accountService.createAccount(account);
        }

        OutcomeType[] types = {
                new OutcomeType("Еда вне дома", new BigDecimal(87000), user),
                new OutcomeType("Бензин", new BigDecimal(141000), user),
                new OutcomeType("Кварплата", new BigDecimal(13000), user)
        };
        for (OutcomeType type : types) {
            outcomeTypeService.addOutcomeType(type);
        }

        LocalDate now = LocalDate.now();
        for (int i = 1; i < 30; i++) {
            double rand = Math.random()*1000;
            outcomeService.addOutcome(new Outcome(new BigDecimal(rand),
                    currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                    new BigDecimal(rand)), LocalDate.of(now.getYear(),now.getMonth(),i), LocalTime.now(),
                    accounts[0], types[0]));
            rand = Math.random()*1000;
            outcomeService.addOutcome(new Outcome(new BigDecimal(rand),
                    currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                            new BigDecimal(rand)), LocalDate.of(now.getYear(),now.getMonth(),i), LocalTime.now(),
                    accounts[0], types[1]));
            rand = Math.random()*1000;
            outcomeService.addOutcome(new Outcome(new BigDecimal(rand),
                    currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                            new BigDecimal(rand)), LocalDate.of(now.getYear(),now.getMonth(),i), LocalTime.now(),
                    accounts[0], types[2]));
        }

        Income[] incomes = {
                new Income(BigDecimal.valueOf(13423), LocalDate.now(), LocalTime.now(), accounts[0]),
                new Income(BigDecimal.valueOf(4324), LocalDate.now(), LocalTime.now(), accounts[1]),
                new Income(BigDecimal.valueOf(13242), LocalDate.now(), LocalTime.now(), accounts[1]),
                new Income(BigDecimal.valueOf(1345435), LocalDate.now(), LocalTime.now(), accounts[0]),
                new Income(BigDecimal.valueOf(234234), LocalDate.now(), LocalTime.now(), accounts[2]),
                new Income(BigDecimal.valueOf(6346), LocalDate.now(), LocalTime.now(), accounts[2]),
                new Income(BigDecimal.valueOf(2356), LocalDate.now(), LocalTime.now(), accounts[0]),
        };

        for (Income income : incomes) {
            incomeService.addIncome(income);
        }
        return new ModelAndView("index");
    }
}
