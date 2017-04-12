package com.epam.internal.daos;

import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ContextConfiguration("classpath:common-mvc-config.xml")
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserDao userDao;

    private static User user;

    @BeforeMethod
    public void setUp() throws Exception {
        user = new User("user@email", "password", new UserInfo("name", "surname"));
        userDao.create(user);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        Assert.assertEquals(user.getEmail(), userDao.getUserByEmail("user@email").getEmail());
    }
}