package com.epam.internal.service;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.User;

import java.util.List;

public interface AccountService {

    List<Account> findAllUserAccounts(User user);
    void createAccount(Account account);
    void updateAccount(Account account);
}
