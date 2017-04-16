package com.epam.internal.services;

import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.*;

@ContextConfiguration("classpath:common-mvc-config.xml")
public class OutcomeTypeServiceTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    private final static String USER_EMAIL = "user@email";

    @BeforeMethod
    public void setUp() throws Exception {
        User user = new User(USER_EMAIL, "password", new UserInfo("name", "surname"));
        userService.createUser(user);
        OutcomeType outcomeType1 = new OutcomeType("food", BigDecimal.valueOf(1000), userService.findByEmail(USER_EMAIL));
        OutcomeType outcomeType2 = new OutcomeType("cinema", BigDecimal.valueOf(2000), userService.findByEmail(USER_EMAIL));
        outcomeTypeService.addOutcomeType(outcomeType1);
        outcomeTypeService.addOutcomeType(outcomeType2);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            userService.deleteUser(user);
        }

    }

    @Test
    public void testGetAvailableOutcomeTypes() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        List<OutcomeType> availableOutcomeTypes = outcomeTypeService.getAvailableOutcomeTypes(user);
        Assert.assertEquals(availableOutcomeTypes.size(), 2);
    }

}