package com.epam.internal.services;

import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.testng.Assert.*;

@ContextConfiguration("classpath:common-mvc-config.xml")
public class IncomeServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    private final static String USER_EMAIL = "user@email";

    private final static LocalDateTime DATE = LocalDateTime.now();

    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User(USER_EMAIL, "password", new UserInfo("name", "surname"));
        userService.createUser(user);
        Account acc1 = new Account("visa", BigDecimal.valueOf(1234), null, userService.findByEmail(USER_EMAIL));
        accountService.createAccount(acc1);
        Income inc1 = new Income(BigDecimal.valueOf(1234), DATE.toLocalDate(), DATE.toLocalTime() ,acc1);
        Income inc2 = new Income(BigDecimal.valueOf(111), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        Income inc3 = new Income(BigDecimal.valueOf(2222), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        Income inc4 = new Income(BigDecimal.valueOf(2222), DATE.toLocalDate(), DATE.toLocalTime(), acc1);
        incomeService.addIncome(inc1);
        incomeService.addIncome(inc2);
        incomeService.addIncome(inc3);
        incomeService.addIncome(inc4);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            userService.deleteUser(user);
        }
    }

    @Test
    public void testFindAllIncomesInAccount() throws Exception {
        Assert.assertEquals(incomeService.findAllIncomesInAccount
                (accountService.findUserAccountByName
                        (userService.findByEmail(USER_EMAIL), "visa")).size(), 4);
    }

    @Test
    public void testFindIncomesInAccountByDate() throws Exception {
        Account account = accountService.findUserAccountByName(userService.findByEmail(USER_EMAIL), "visa");
        Assert.assertEquals(incomeService.findIncomesInAccountByDate(account, DATE.toLocalDate()).size(), 4);
    }

    @Test
    public void testCascadeDeleting() throws Exception {
        userService.deleteUser(userService.findByEmail(USER_EMAIL));
        Assert.assertEquals(incomeService.findAllIncomesInAccount
                (accountService.findUserAccountByName
                        (userService.findByEmail(USER_EMAIL), "visa")).size(), 0);
    }

    @Test
    public void testIncomesPaging() throws Exception {
        PagedListHolder<Income> pagedList = incomeService.getPagedIncomeList
                (accountService.findUserAccountByName
                        (userService.findByEmail(USER_EMAIL), "visa"), 3);
        pagedList.setPage(0);
        Assert.assertEquals(pagedList.getPageList().size(), 3);
        pagedList.setPage(1);
        Assert.assertEquals(pagedList.getPageList().size(), 1);
        Assert.assertEquals(pagedList.getPageCount(), 2);

    }

}