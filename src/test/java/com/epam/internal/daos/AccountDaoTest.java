package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

@ContextConfiguration("classpath:common-mvc-config.xml")
public class AccountDaoTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    @Autowired
    SessionFactory sessionFactory;

    public AccountDaoTest() {
        System.out.println("**************************");
    }

    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User("user@email", "password", new UserInfo("name", "surname"));
        userService.createUser(user);
        Account acc1 = new Account("visa", BigDecimal.valueOf(1234), null, user);
        Account acc2 = new Account("mastercard", BigDecimal.valueOf(4321), null, user);
        accountDao.create(acc1);
        accountDao.create(acc2);
    }

    // TODO: 13.04.17 Move all tests from here 
    @Test
    public void testFindAllUserAccounts() throws Exception {
        final int ACCOUNTS_AMOUNT = 2;
        User user = userService.findByEmail("user@email");
        Assert.assertEquals(ACCOUNTS_AMOUNT, user.getAccounts().size());
        userService.deleteUser(user);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAccountDelete() throws Exception {
        User user = userService.findByEmail("user@email");
        userService.deleteUser(user);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Account> accounts = session.createQuery("Select a From Account a where a.user = :user").setParameter("user", user).getResultList();
        session.getTransaction().commit();
        session.close();
        Assert.assertEquals(0, accounts.size());
    }

    //TODO: 13.04.17 Need replace in another class without BeforeMethod
    @Test
    public void testSearchInEmptyTable() throws Exception{
        userService.deleteUser(userService.findByEmail("user@email"));
        User user = userService.findByEmail("user@email");
        Assert.assertNull(accountService.findUserAccountByName(user, "visa"));
        Assert.assertEquals(0, accountService.findAllUserAccounts(user).size());
    }
}