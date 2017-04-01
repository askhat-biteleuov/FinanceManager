package com.epam.internal.service;

import com.epam.internal.data.entities.Account;

public interface AccountService {

    Account findById(int id);
    void createAccount(Account account);
    void deleteAccountById(int id);
    void deleteAccount(Account account);
}
