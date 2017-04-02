package com.epam.internal.service;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.User;

import java.util.List;

public interface IAccountService {

    Account findById(int id);
    List<Account> findAllUserAccounts(User user);
    void createAccount(Account account);
    void deleteAccountById(int id);
    void updateAccount(Account account);
}
