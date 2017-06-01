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
        for (int i = 1; i <= now.lengthOfMonth(); i++) {
            switch (i) {
                case 1:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(250),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(250)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 2:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(160),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(160)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 3:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(245),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(245)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(120),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(120)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 4:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(350),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(350)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 5:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(120),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(120)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(200),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(200)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(500),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(500)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 6:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(200),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(200)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 7:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(180),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(180)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 8:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(150),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(150)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 9:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(245),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(245)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(400),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(400)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 10:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(220),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(220)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 11:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(190),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(190)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(40),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(40)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 12:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(240),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(240)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 13:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(280),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(280)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 14:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(120),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(120)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 15:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(160),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(160)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(350),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(350)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 16:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(240),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(240)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(40),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(40)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 17:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(140),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(140)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(220),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(220)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 18:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(165),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(165)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 19:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(300),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(300)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 20:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(220),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(220)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 21:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(310),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(310)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(120),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(120)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 22:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(250),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(250)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(160),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(160)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 23:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(270),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(270)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(40),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(40)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 24:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(165),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(165)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(500),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(500)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 25:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(210),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(210)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(180),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(180)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 26:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(200),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(200)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(150),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(150)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 27:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(110),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(110)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 28:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(170),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(170)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(80),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(80)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 29:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(210),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(210)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 30:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(245),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(245)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(160),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(160)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(0),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(0)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;
                case 31:
                    outcomeService.addOutcome(new Outcome(new BigDecimal(240),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(240)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[0]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(150),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(150)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[1]));
                    outcomeService.addOutcome(new Outcome(new BigDecimal(400),
                            currencyService.getOutcomeAmountForDefaultCurrency(accounts[0],
                                    new BigDecimal(400)), LocalDate.of(now.getYear(), now.getMonth(), i), LocalTime.now(),
                            accounts[0], types[2]));
                    break;


            }
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
