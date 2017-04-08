package com.epam.internal.services.implementation;


import com.epam.internal.daos.AccountDao;
import com.epam.internal.dtos.AccountDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAllUserAccounts(User user) {
        return accountDao.findAllUserAccounts(user);
    }

    @Override
    public Account findUserAccountByName(User user, String name) {
        return accountDao.findUserAccountByName(user, name);
    }

    @Override
    public void createAccount(Account account) {
        accountDao.create(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    @Override
    public void deleteAccount(Account account) {
        accountDao.delete(account);
    }

    @Override
    public void createAccount(AccountDto accountDto, User user) {
        Account account = new Account(accountDto.getName(), new BigDecimal(accountDto.getBalance()), null, user);
        accountDao.create(account);
    }
}