package com.epam.internal.services.implementation;

import com.epam.internal.daos.AccountDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class AccountServiceImplTest {

    @Mock
    private AccountDao dao;

    @InjectMocks
    private AccountServiceImpl service;

    @Spy
    private List<Account> accounts = new ArrayList<>();

    private static User user = new User("user@email", "password", new UserInfo("name", "surname"));
    private static Account acc1;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        accounts = getAccountList();
    }

    @Test
    public void testFindAllUserAccounts() throws Exception {
        when(dao.findAllUserAccounts(user)).thenReturn(accounts);
        Assert.assertEquals(service.findAllUserAccounts(user), accounts);
        verify(dao, times(1)).findAllUserAccounts(user);
    }

    @Test
    public void testFindUserAccountByName() throws Exception {
        when(dao.findUserAccountByName(any(User.class), anyString())).thenReturn(acc1);
        Assert.assertEquals(service.findUserAccountByName(user, "visa"), acc1);
        verify(dao, times(1)).findUserAccountByName(any(User.class), anyString());
    }

    private List<Account> getAccountList() {
        acc1 = new Account("visa", BigDecimal.valueOf(1234), null, user);
        Account acc2 = new Account("mastercard", BigDecimal.valueOf(4321), null, user);
        accounts.add(acc1);
        accounts.add(acc2);
        return accounts;
    }


}