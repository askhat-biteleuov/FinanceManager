package com.epam.internal.services.implementation;

import com.epam.internal.models.*;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.testng.Assert.*;

@ContextConfiguration(locations = {"classpath:common-mvc-config.xml"})
public class UserServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User("user@email", "password", new UserInfo("name", "lastname"));
        userService.createUser(user);
        User user2 = new User("user2@email", "password", new UserInfo("namee", "lastnamee"));
        userService.createUser(user2);

        Account account = new Account("visa", new BigDecimal(2323), null, user);
        accountService.createAccount(account);
        Account account2 = new Account("mastercard", new BigDecimal(2343), null, user);
        accountService.createAccount(account2);
        Account account3 = new Account("visa", new BigDecimal(23243), null, user2);
        accountService.createAccount(account3);

        OutcomeType food = new OutcomeType("food", BigDecimal.valueOf(5000), user);
        outcomeTypeService.addOutcomeType(food);
        OutcomeType cinema = new OutcomeType("cinema", BigDecimal.valueOf(1500), user2);
        outcomeTypeService.addOutcomeType(cinema);
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = userService.findByEmail("user@email");
        User user2 = userService.findByEmail("user2@email");
        userService.deleteUser(user);
        userService.deleteUser(user2);

        Assert.assertNull(userService.findByEmail("user@email"));
        Assert.assertNull(userService.findByEmail("user2@email"));

        Assert.assertEquals(0, accountService.findAllUserAccounts(user).size());
        Assert.assertEquals(0, accountService.findAllUserAccounts(user2).size());

        Assert.assertEquals(0, outcomeTypeService.getAvailableOutcomeTypes(user).size());
        Assert.assertEquals(0, outcomeTypeService.getAvailableOutcomeTypes(user2).size());

    }

}