package com.fm.internal.services;

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
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@ContextConfiguration("classpath:spring-utils.xml")
public class OutcomeTypeServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @Autowired
    private OutcomeService outcomeService;

    private final static String USER_EMAIL = "user@email";
    private final static String USER_PASSWORD = "password";
    private final static String FIRST_ACCOUNT_NAME = "Visa";
    private final static String SECOND_ACCOUNT_NAME = "Mastercard";
    private final static String THIRD_ACCOUNT_NAME = "Мир";


    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User(USER_EMAIL, USER_PASSWORD, new UserInfo("name", "surname"));
        userService.createUser(user);
        Account[] accounts = {
                new Account(FIRST_ACCOUNT_NAME, new BigDecimal(29999), null, user),
                new Account(SECOND_ACCOUNT_NAME, new BigDecimal(3000), null, user),
                new Account(THIRD_ACCOUNT_NAME, new BigDecimal(5752), null, user)
        };
        for (Account account : accounts) {
            accountService.createAccount(account);
        }

        OutcomeType[] types = {
                new OutcomeType("Еда вне дома", new BigDecimal(3000), user),
                new OutcomeType("ФФСБ", new BigDecimal(2323), user),
                new OutcomeType("dfsfsd", new  BigDecimal(3242), user)
        };
        for (OutcomeType type : types) {
            outcomeTypeService.addOutcomeType(type);
        }

        Outcome[] outcomes = {
                new Outcome(new BigDecimal(2122), LocalDate.now(), LocalTime.now(), accounts[0], types[0]),
                new Outcome(new BigDecimal(332), LocalDate.now(), LocalTime.now(), accounts[0], types[0]),
                new Outcome(new BigDecimal(4144), LocalDate.now(), LocalTime.now(), accounts[0], types[1]),
                new Outcome(new BigDecimal(9999), LocalDate.now(), LocalTime.now(), accounts[0], types[1]),
                new Outcome(new BigDecimal(50), LocalDate.now(), LocalTime.now(), accounts[0], types[2]),
                new Outcome(new BigDecimal(1234), LocalDate.now(), LocalTime.now(), accounts[0], types[2]),
                new Outcome(new BigDecimal(2222), LocalDate.now(), LocalTime.now(), accounts[0], types[2]),
                new Outcome(new BigDecimal(3999), LocalDate.now(), LocalTime.now(), accounts[0], types[0]),
        };
        for (Outcome outcome : outcomes) {
            outcomeService.addOutcome(outcome);
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            List<Account> accounts = accountService.findAllUserAccounts(user);
            accounts.stream().forEach(
                    account -> {outcomeService.findAllOutcomesInAccount(account).stream().forEach(
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
    public void testGetOutcomeTypesValue() throws Exception{
        User user = userService.findByEmail(USER_EMAIL);
        Account account = accountService.findUserAccountByName(user, FIRST_ACCOUNT_NAME);
        Assert.assertNotNull(outcomeTypeService.countOutcomeTypesValueByDate(account, LocalDate.of(2017, 4, 23), LocalDate.of(2017, 4, 26)));
        outcomeTypeService.countOutcomeTypesValueByDate(account, LocalDate.of(2017, 4, 23), LocalDate.of(2017, 4, 26)).entrySet().stream().forEach(e -> System.out.printf(e.getKey()+" %f%n", e.getValue()));
    }

    @Test
    public void testGetAvailableOutcomeTypes() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        List<OutcomeType> availableOutcomeTypes = outcomeTypeService.getAvailableOutcomeTypes(user);
        Assert.assertEquals(availableOutcomeTypes.size(), 9);
    }

}