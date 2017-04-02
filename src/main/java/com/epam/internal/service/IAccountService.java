package com.epam.internal.service;

import com.epam.internal.data.entities.Account;

import java.util.List;

public interface IAccountService {

    Account findById(int id);
    List<Account> findAll();
    void createAccount(Account account);
    void deleteAccountById(int id);
    void update(Account account);
}
