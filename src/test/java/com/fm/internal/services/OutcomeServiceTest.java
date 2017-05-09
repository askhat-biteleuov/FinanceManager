package com.fm.internal.services;

import com.fm.internal.daos.CurrencyDao;
import com.fm.internal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration("classpath:spring-utils.xml")
public class OutcomeServiceTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyDao currencyDao;

    private final static String USER_EMAIL = "user@email";
    private final static String USER_PASSWORD = "password";
    private final static String FIRST_ACCOUNT_NAME = "visa";
    private final static String SECOND_ACCOUNT_NAME = "mastercard";

    @BeforeMethod
    public void setUp() throws Exception {
        currencyDao.saveOrUpdate(new Currency("Российский рубль", BigDecimal.ONE, BigDecimal.ONE,
                643, "RUB"));
        Currency currency = currencyService.findCurrencyByCharCode("RUB");
        User user = new User(USER_EMAIL, USER_PASSWORD, new UserInfo("name", "surname", currency));
        userService.createUser(user);
        Account[] accounts = {
                new Account(FIRST_ACCOUNT_NAME, new BigDecimal(29999), null, user,
                        currencyService.findCurrencyByCharCode("RUB")),
                new Account(SECOND_ACCOUNT_NAME, new BigDecimal(3000), null, user,
                        currencyService.findCurrencyByCharCode("RUB")),
        };
        Arrays.stream(accounts).forEach(account -> accountService.createAccount(account));
        OutcomeType type = new OutcomeType("Еда вне дома", new BigDecimal(3000), user);
        outcomeTypeService.addOutcomeType(type);

        Outcome[] outcomes = {
                new Outcome(new BigDecimal(1), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(1)), LocalDate.now(), LocalTime.now(), "blahblahblahblah#weekend blahblahblah", accounts[0], type),
                new Outcome(new BigDecimal(2), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(2)), LocalDate.now(), LocalTime.now(), "blahblahblahblah#weekend#summertime blahblahblah", accounts[0], type),
                new Outcome(new BigDecimal(3), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(3)), LocalDate.now(), LocalTime.now(), "blahblahblahblah#weekendblahblahblah", accounts[0], type),
                new Outcome(new BigDecimal(4), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(4)), LocalDate.now(), LocalTime.now(), "blahblahblahblah weekend blahblahblah", accounts[0], type),
                new Outcome(new BigDecimal(5), currencyService.getOutcomeAmountForDefaultCurrency(accounts[1], new BigDecimal(5)), LocalDate.now(), LocalTime.now(), accounts[1], type),
                new Outcome(new BigDecimal(6), currencyService.getOutcomeAmountForDefaultCurrency(accounts[1], new BigDecimal(6)), LocalDate.of(2017, 04, 20), LocalTime.now(), accounts[1], type),
                new Outcome(new BigDecimal(7), currencyService.getOutcomeAmountForDefaultCurrency(accounts[1], new BigDecimal(7)), LocalDate.of(2017, 04, 20), LocalTime.now(), accounts[1], type),
                new Outcome(new BigDecimal(8), currencyService.getOutcomeAmountForDefaultCurrency(accounts[1], new BigDecimal(8)), LocalDate.of(2017, 04, 20), LocalTime.now(), accounts[1], type),
                new Outcome(new BigDecimal(9), currencyService.getOutcomeAmountForDefaultCurrency(accounts[1], new BigDecimal(9)), LocalDate.now(), LocalTime.now(), accounts[1], type),
                new Outcome(new BigDecimal(10), currencyService.getOutcomeAmountForDefaultCurrency(accounts[1], new BigDecimal(10)), LocalDate.now(), LocalTime.now(), accounts[1], type)
        };
        Arrays.stream(outcomes).forEach(outcome -> outcomeService.addOutcome(outcome));
    }

    @AfterMethod
    public void tearDown() throws Exception{
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            List<Account> accounts = accountService.findAllUserAccounts(user);
            accounts.stream().forEach(
                    account -> {
                        outcomeService.findAllOutcomesInAccount(account).stream().forEach(
                                outcome -> outcomeService.deleteOutcome(outcome));
                        accountService.deleteAccount(account);

                    }
            );
            outcomeTypeService.getAvailableOutcomeTypes(user).stream().forEach(outcomeType -> outcomeTypeService.deleteOutcomeType(outcomeType));
            user = userService.findByEmail(USER_EMAIL);
            userService.deleteUser(user);
        }
    }

    @Test
    public void hashcodeSearchTest() throws Exception{
        User user = userService.findByEmail(USER_EMAIL);
        Account account = accountService.findUserAccountByName(user, FIRST_ACCOUNT_NAME);
        Assert.assertEquals(2, outcomeService.getOutcomesByHashCode(account, "#weekend").size());
        outcomeService.getOutcomesByHashCode(account, "#weekend").stream().forEach(outcome -> System.out.println(outcome.getNote()));
    }
}
