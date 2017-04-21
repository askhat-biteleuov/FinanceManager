package com.epam.internal.services;

import com.epam.internal.daos.UserDao;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@ContextConfiguration("classpath:spring-utils.xml")
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    private static User user;

    private final static String USER_EMAIL = "user@email";
    private final static String USER_PASS = "password";

    @BeforeMethod
    public void setUp() throws Exception {
        user = new User(USER_EMAIL, USER_PASS, new UserInfo("name", "surname"));
        userService.createUser(user);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User reload = userService.findByEmail(USER_EMAIL);
        if (reload != null) {
            userService.deleteUser(user);
        }
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        Assert.assertEquals(USER_EMAIL, userService.findByEmail(USER_EMAIL).getEmail());
    }

    @Test
    public void testUserDataInitialization(){
        Assert.assertNotNull(userService.findByEmail(USER_EMAIL).getAccounts());
        Assert.assertEquals(6, userService.findByEmail(USER_EMAIL).getOutcomeTypes().size());
    }

}