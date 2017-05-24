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

    @Autowired
    private CurrencyService currencyService;

    private final static String FIRST_USER_EMAIL = "user@email";
    private final static String FIRST_USER_PASSWORD = "password";
    private final static String SECOND_USER_EMAIL = "user2@email";
    private final static String SECOND_USER_PASSWORD = "password";
    private final static String FIRST_ACCOUNT_NAME = "Visa";
    private final static String SECOND_ACCOUNT_NAME = "Mastercard";
    private final static String THIRD_ACCOUNT_NAME = "Мир";


    @BeforeMethod
    public void setUp() throws Exception {
        Currency currency = new Currency("Рубль", BigDecimal.ONE, BigDecimal.ONE, 999, "RUB");
        currencyService.addOrUpdateCurrency(currency);
        User user = new User(FIRST_USER_EMAIL, FIRST_USER_PASSWORD, new UserInfo("name", "surname", currencyService.findCurrencyByCharCode("RUB")));
        User secondUser = new User(SECOND_USER_EMAIL, SECOND_USER_PASSWORD, new UserInfo("name", "surname", currencyService.findCurrencyByCharCode("RUB")));
        userService.createUser(user);
        userService.createUser(secondUser);
        Account[] accounts = {
                new Account(FIRST_ACCOUNT_NAME, new BigDecimal(29999), null, user,
                        currencyService.findCurrencyByCharCode("RUB")),
                new Account(SECOND_ACCOUNT_NAME, new BigDecimal(3000), null, user,
                        currencyService.findCurrencyByCharCode("RUB")),
                new Account(THIRD_ACCOUNT_NAME, new BigDecimal(5752), null, user,
                        currencyService.findCurrencyByCharCode("RUB")),
                new Account(THIRD_ACCOUNT_NAME, new BigDecimal(1111), null, secondUser,
                        currencyService.findCurrencyByCharCode("RUB"))
        };
        for (Account account : accounts) {
            accountService.createAccount(account);
        }

        OutcomeType[] types = {
                new OutcomeType("Еда вне дома", new BigDecimal(3000), user),
                new OutcomeType("ФФСБ", new BigDecimal(2323), user),
                new OutcomeType("dfsfsd", new  BigDecimal(3242), user, false),
                new OutcomeType("Еда вне дома", new BigDecimal(3000), secondUser),
        };
        for (OutcomeType type : types) {
            outcomeTypeService.addOutcomeType(type);
        }

        Outcome[] outcomes = {
                new Outcome(new BigDecimal(1), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(1)), LocalDate.now(), LocalTime.now(), accounts[0], types[0]),
                new Outcome(new BigDecimal(2), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(2)), LocalDate.now(), LocalTime.now(), accounts[0], types[0]),
                new Outcome(new BigDecimal(3), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(3)), LocalDate.now(), LocalTime.now(), accounts[0], types[2]),
                new Outcome(new BigDecimal(4), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(4)), LocalDate.now(), LocalTime.now(), accounts[0], types[2]),
                new Outcome(new BigDecimal(5), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(5)), LocalDate.now(), LocalTime.now(), accounts[0], types[2]),
                new Outcome(new BigDecimal(6), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(6)), LocalDate.of(2017, 04, 20), LocalTime.now(), accounts[0], types[0]),
                new Outcome(new BigDecimal(7), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(7)), LocalDate.of(2017, 04, 20), LocalTime.now(), accounts[0], types[1]),
                new Outcome(new BigDecimal(8), currencyService.getOutcomeAmountForDefaultCurrency(accounts[0], new BigDecimal(8)), LocalDate.of(2017, 04, 20), LocalTime.now(), accounts[0], types[1]),
                new Outcome(new BigDecimal(9), currencyService.getOutcomeAmountForDefaultCurrency(accounts[3], new BigDecimal(9)), LocalDate.now(), LocalTime.now(), accounts[3], types[3]),
                new Outcome(new BigDecimal(10), currencyService.getOutcomeAmountForDefaultCurrency(accounts[3], new BigDecimal(10)), LocalDate.now(), LocalTime.now(), accounts[3], types[3])
        };
        for (Outcome outcome : outcomes) {
            outcomeService.addOutcome(outcome);
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User user = userService.findByEmail(FIRST_USER_EMAIL);
        if (user != null) {
            List<Account> accounts = accountService.findAllUserAccounts(user);
            accounts.stream().forEach(
                    account -> {outcomeService.findAllOutcomesInAccount(account).stream().forEach(
                        outcome -> outcomeService.deleteOutcome(outcome));
                        accountService.deleteAccount(account);
                    }
            );
            outcomeTypeService.getAvailableOutcomeTypes(user).stream().forEach(outcomeType -> outcomeTypeService.deleteOutcomeType(outcomeType));
            user = userService.findByEmail(FIRST_USER_EMAIL);
            userService.deleteUser(user);
        }
        user = userService.findByEmail(SECOND_USER_EMAIL);
        if (user != null) {
            List<Account> accounts = accountService.findAllUserAccounts(user);
            accounts.stream().forEach(
                    account -> {outcomeService.findAllOutcomesInAccount(account).stream().forEach(
                        outcome -> outcomeService.deleteOutcome(outcome));
                        accountService.deleteAccount(account);
                    }
            );
            outcomeTypeService.getAvailableOutcomeTypes(user).stream().forEach(outcomeType -> outcomeTypeService.deleteOutcomeType(outcomeType));
            user = userService.findByEmail(SECOND_USER_EMAIL);
            userService.deleteUser(user);
        }
    }

    @Test
    public void testGetOutcomeTypesValue() throws Exception{
        User user = userService.findByEmail(FIRST_USER_EMAIL);
        Account account = accountService.findUserAccountByName(user, FIRST_ACCOUNT_NAME);
        Assert.assertNotNull(outcomeTypeService.countOutcomeTypesValueByDate(account, LocalDate.of(2017, 4, 23), LocalDate.of(2017, 4, 26)));
        outcomeTypeService.countOutcomeTypesValueByDate(account, LocalDate.of(2017, 4, 20), LocalDate.now()).entrySet().stream().forEach(e -> System.out.printf(e.getKey()+" %f%n", e.getValue()));
    }

    @Test
    public void testGetAvailableOutcomeTypes() throws Exception {
        User user = userService.findByEmail(FIRST_USER_EMAIL);
        List<OutcomeType> availableOutcomeTypes = outcomeTypeService.getAvailableOutcomeTypes(user);
        Assert.assertEquals(availableOutcomeTypes.size(), 8);
        availableOutcomeTypes.forEach(outcomeType -> System.out.println(outcomeType.getName()));
    }

    @Test
    public void testGetUserOutcomes() throws Exception {
        User user = userService.findByEmail(FIRST_USER_EMAIL);
        List<Outcome> userOutcomes = outcomeService.getUserOutcomes(user);
        Assert.assertNotNull(userOutcomes);
        userOutcomes.stream().forEach(outcome -> System.out.println(outcome.getAmount()));
    }
}