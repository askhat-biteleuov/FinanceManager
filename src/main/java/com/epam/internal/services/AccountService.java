package com.epam.internal.services;

import com.epam.internal.models.Account;
import com.epam.internal.models.User;

import java.util.List;

public interface AccountService {

    List<Account> findAllUserAccounts(User user);

    Account findUserAccountByName(User user, String name);

    void createAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Account account);
}
