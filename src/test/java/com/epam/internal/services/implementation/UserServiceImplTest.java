package com.epam.internal.services.implementation;

import com.epam.internal.daos.UserDao;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.UserService;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    private static User user;
    @Test
    public void testCreateUser() throws Exception {
        doNothing().when(userDao).create(any(User.class));
        userService.createUser(users.get(0));

        verify(userDao, times(1)).create(captor.capture());
        Assert.assertEquals(captor.getValue().getEmail(), "user@email");
        Assert.assertEquals(4, users.size());
        verify(users, times(4)).add(any(User.class));
    }

    @Test
    public void testFindByEmail() throws Exception {
        when(userDao.getUserByEmail(anyString())).thenReturn(user);
        Assert.assertEquals(userService.findByEmail("user@email"), user);
        verify(userDao, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void testDeleteUserByEmail() throws Exception {
        when(userDao.getUserByEmail(anyString())).thenReturn(user);
        doNothing().when(userDao).delete(any(User.class));
        userService.deleteUserByEmail("user@email");
        verify(userDao, times(1)).delete(any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userDao).delete(any(User.class));
        userService.deleteUser(users.get(0));
        verify(userDao, times(1)).delete(any(User.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        doNothing().when(userDao).update(any(User.class));
        userService.updateUser(users.get(0));
        verify(userDao, times(1)).update(any(User.class));
    }

    @Test
    public void testUpdateUserInfo() throws Exception {
        doNothing().when(userDao).update(any(User.class));
        userService.updateUserInfo(users.get(0), any(UserInfo.class));
        verify(userDao, times(1)).update(any(User.class));
    }

    private List<User> getUserList() {
        user = new User("user@email", "password", new UserInfo("name", "lastname"));
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