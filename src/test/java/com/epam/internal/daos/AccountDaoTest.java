package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.Table;
import java.math.BigDecimal;

import static org.testng.Assert.*;

@ContextConfiguration("classpath:common-mvc-config.xml")
public class AccountDaoTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserService userService;

    private static User user;
    private static Account acc1;

    @BeforeClass
    public void setUp() throws Exception {
        user = new User("user@email", "password", new UserInfo("name", "surname"));
        userService.createUser(user);
        acc1 = new Account("visa", BigDecimal.valueOf(1234), null, user);
        Account acc2 = new Account("mastercard", BigDecimal.valueOf(4321), null, user);
        accountDao.create(acc1);
        accountDao.create(acc2);
    }

    @Test
    public void testFindAllUserAccounts() throws Exception {
        final int ACCOUNTS_AMOUNT = 2;
        Assert.assertEquals(ACCOUNTS_AMOUNT, accountDao.findAllUserAccounts(user).size());
    }

    @Test
    public void testFindUserAccountByName() throws Exception {
        Assert.assertEquals(acc1.getName(), accountDao.findUserAccountByName(user, "visa").getName());
        Assert.assertEquals(acc1.getBalance().intValue(), accountDao.findUserAccountByName(user, "visa").getBalance().intValue());
        Assert.assertEquals(acc1.getUser().getEmail(), accountDao.findUserAccountByName(user, "visa").getUser().getEmail());
    }

    @Test(dependsOnMethods = {"testFindUserAccountByName", "testFindAllUserAccounts"})
    public void testAccountDelete() throws Exception {
        User user1 = userService.findByEmail("user@email");
        userService.deleteUser(user1);
        Assert.assertEquals(0,accountDao.findAllUserAccounts(user1).size());
    }

    @Test(dependsOnMethods = "testAccountDelete")
    public void testSearchInEmptyTable() throws Exception{
        Assert.assertNull(accountDao.findUserAccountByName(user, "visa"));
        Assert.assertEquals(0, accountDao.findAllUserAccounts(user).size());
    }
}