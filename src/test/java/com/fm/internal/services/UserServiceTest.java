package com.fm.internal.services;

import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:spring-utils.xml")
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;

    private static User user;
    private static User user2;

    private final static String USER_EMAIL = "user@email";
    private final static String USER_PASS = "password";
    private final static String SECOND_USER_EMAIL = "seconduser@email";
    private final static String SECOND_USER_PASS = "password";

    @BeforeMethod
    public void setUp() throws Exception {
        user = new User(USER_EMAIL, USER_PASS, new UserInfo("name", "surname"));
        userService.createUser(user);
        user2 = new User(SECOND_USER_EMAIL, SECOND_USER_PASS, new UserInfo("name", "surname"));
        userService.createUser(user2);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User reload = userService.findByEmail(USER_EMAIL);
        if (reload != null) {
            userService.deleteUser(user);
        }
        reload = userService.findByEmail(SECOND_USER_EMAIL);
        if (reload != null) {
            userService.deleteUser(user2);
        }
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        Assert.assertEquals(USER_EMAIL, userService.findByEmail(USER_EMAIL).getEmail());
    }

    @Test
    public void testUserDataInitialization(){
        Assert.assertNotNull(accountService.findAllUserAccounts(userService.findByEmail(USER_EMAIL)));
        Assert.assertEquals(6, outcomeTypeService.getAvailableOutcomeTypes(userService.findByEmail(USER_EMAIL)).size());
        Assert.assertNotNull(accountService.findAllUserAccounts(userService.findByEmail(SECOND_USER_EMAIL)));
        Assert.assertEquals(6, outcomeTypeService.getAvailableOutcomeTypes(userService.findByEmail(SECOND_USER_EMAIL)).size());
    }

}