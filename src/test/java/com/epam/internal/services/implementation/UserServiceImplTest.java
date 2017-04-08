package com.epam.internal.services.implementation;

import com.epam.internal.daos.IncomeDao;
import com.epam.internal.daos.UserDao;
import com.epam.internal.models.*;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.IncomeService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private  UserService userService = new UserServiceImpl();

    @Spy
    private List<User> users = new ArrayList<>();

    @Captor
    private ArgumentCaptor<User> captor;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        users = getUserList();
    }
    @Test
    public void testCreateUser() throws Exception {
        doNothing().when(userDao).create(any(User.class));
        userService.createUser(users.get(0));

        verify(userDao, times(1)).create(captor.capture());
        Assert.assertEquals(captor.getValue().getEmail(), "user@email");
        Assert.assertEquals(4, users.size());
        verify(users, times(4)).add(any(User.class));
    }
    private List<User> getUserList() {
        User user = new User("user@email", "password", new UserInfo("name", "lastname"));
        User user1 = new User("user1@email", "password", new UserInfo("name1", "lastname1"));
        User user2 = new User("user2@email", "password", new UserInfo("name2", "lastname2"));
        User user3 = new User("user3@email", "password", new UserInfo("name3", "lastname3"));
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

}