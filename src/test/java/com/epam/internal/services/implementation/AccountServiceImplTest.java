package com.epam.internal.services.implementation;

import com.epam.internal.models.Account;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeTypeService;
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

@ContextConfiguration(locations = {"classpath:common-mvc-config.xml"})
public class AccountServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User("email", "pass", new UserInfo("firstname", "lastname"));
        userService.createUser(user);
        Account account = new Account("visa", BigDecimal.valueOf(5000), null, user);
        Account account1 = new Account("credit", BigDecimal.valueOf(2000), null, user);
        accountService.createAccount(account);
        accountService.createAccount(account1);
        OutcomeType type = new OutcomeType("food", BigDecimal.valueOf(900), user);
        outcomeTypeService.addOutcomeType(type);
    }

    @Test
    public void testFindAllUserAccounts() throws Exception {
        Session curSes = sessionFactory.openSession();
        User user = userService.findByEmail("email");
        Assert.assertEquals(2, accountService.findAllUserAccounts(user).size());
        Assert.assertEquals(2, user.getAccounts().size());
        Assert.assertEquals(1, outcomeTypeService.getAvailableOutcomeTypes(user).size());
        curSes.close();
    }

}