package com.epam.internal.services;

import com.epam.internal.models.Account;
import com.epam.internal.models.User;

import java.util.List;

public interface AccountService {

    List<Account> findAllUserAccounts(User user);
    void createAccount(Account account);
    void updateAccount(Account account);
}
