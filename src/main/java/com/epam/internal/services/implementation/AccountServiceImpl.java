package com.epam.internal.services.implementation;


import com.epam.internal.daos.AccountDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAllUserAccounts(User user) {
        return accountDao.findAllUserAccounts(user);
    }

    @Override
    public void createAccount(Account account) {
        accountDao.create(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.update(account);
    }
}