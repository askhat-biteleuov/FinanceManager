package com.epam.internal.dao;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.User;

import java.util.List;

public interface IAccountDao {

    void updateInfo(Account account);

    void saveAccount(Account account);

    void deleteAccount(Account account);

    List<Account> findAllUserAccounts(User user);
}
