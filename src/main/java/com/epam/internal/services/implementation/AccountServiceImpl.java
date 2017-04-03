package com.epam.internal.services.implementation;

import com.epam.internal.daos.AccountDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao dao;

    @Override
    public List<Account> findAllUserAccounts(User user) {
        return dao.findAllUserAccounts(user);
    }

    @Override
    public void createAccount(Account account) {
        dao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        dao.updateInfo(account);
    }


}
