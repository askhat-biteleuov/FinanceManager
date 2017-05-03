package com.fm.internal.services;

import com.fm.internal.currency.dao.CurrencyDao;
import com.fm.internal.currency.model.Currency;
import com.fm.internal.models.Account;
import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

@ContextConfiguration("classpath:spring-utils.xml")
public class CurrencyServiceTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyDao currencyDao;

    private final static String USER_EMAIL = "user@email";
    private final static String USER_PASS = "password";

    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User(USER_EMAIL, USER_PASS, new UserInfo("name", "surname"));
        userService.createUser(user);
        Currency currency = new Currency("Nuka-Cola Bottle caps", BigDecimal.ONE, BigDecimal.TEN, 1000, "NCC");
        currencyDao.saveOrUpdate(currency);
        Account acc1 = new Account("visa", BigDecimal.valueOf(1234), null, userService.findByEmail(USER_EMAIL), currencyService.findCurrencyByCharCode("NCC"));
        Account acc2 = new Account("mastercard", BigDecimal.valueOf(4321), null, userService.findByEmail(USER_EMAIL), currencyService.findCurrencyByCharCode("NCC"));
        accountService.createAccount(acc1);
        accountService.createAccount(acc2);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        currencyService.deleteCurrency(currencyService.findCurrencyByCharCode("NCC"));
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            userService.deleteUser(user);
        }

    }

    @Test
    public void testCurrencyCreate() throws Exception {
        Assert.assertNotNull(currencyService.findCurrencyByCharCode("NCC"));
        System.out.println(currencyService.findCurrencyByCharCode("NCC").getName());
    }

    @Test
    public void testCurrencyAccountRelation() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        List<Account> userAccounts = accountService.findAllUserAccounts(user);
        Assert.assertNotNull(userAccounts.get(0).getCurrency());
    }
}
