package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ContextConfiguration("classpath:common-mvc-config.xml")
public class IncomeDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private IncomeDao incomeDao;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;


    private User user = new User("email", "pass", new UserInfo("firstName", "lastName"));
    private Account account = new Account("visa", BigDecimal.valueOf(5000), null, user);

    @BeforeMethod
    public void setUp() {
        userService.createUser(user);
        accountService.createAccount(account);
    }

    @Test
    public void testCreate() {
        Income income = new Income(BigDecimal.valueOf(300), LocalDateTime.of(1994, 2, 4, 12, 45), account);

        incomeDao.create(income);

        Assert.assertNotNull(income.getId());
        Income reloaded = getIncome(1);
        Assert.assertEquals(income.getId(), reloaded.getId());
        Assert.assertEquals(income.getDate(), reloaded.getDate());
    }

    @Test(enabled = false)
    public void testGetAccountsIncomes() throws Exception {
    }

    private Income getIncome(long id) {
        return incomeDao.findyById(id);
    }

}